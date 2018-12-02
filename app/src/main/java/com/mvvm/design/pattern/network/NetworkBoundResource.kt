/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mvvm.design.pattern.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.mvvm.design.pattern.utils.Constants

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        if (isFetchFromDb()) {
            @Suppress("LeakingThis")
            val dbSource = loadFromDb()
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (isFetchFromNetwork(data)) {
                    fetchFromNetwork(dbSource)
                } else {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.success(newData))
                    }
                }
            }
        } else {
            fetchFromNetwork(null)
        }

    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }


    private fun fetchFromNetwork(dbSource: LiveData<ResultType>?) {
        val apiResponse = createCall()

        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        dbSource?.let {
            result.addSource(dbSource) { newData ->
                setValue(Resource.loading(newData))
            }
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            dbSource?.let {
                result.removeSource(dbSource)
            }
            when (response) {
                is ApiSuccessResponse -> {
                    onApiSuccess(response)
                }
                is ApiEmptyResponse -> {
                    onEmptyApiResponse()
                }
                is ApiErrorResponse -> {
                    onApiError(dbSource, response)
                }
            }
        }
    }

    private fun onApiSuccess(response: ApiSuccessResponse<RequestType>) {
        appExecutors.diskIO().execute {
            if (isSaveInDb()) {
                saveCallResult(processResponse(response))
            }
            appExecutors.mainThread().execute {
                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.
                if (isFetchFromDb()) {
                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.success(newData))
                    }
                } else {
                    setValue(Resource.success(getData(response.body)))
                }
            }
        }
    }

    private fun onEmptyApiResponse() {
        appExecutors.mainThread().execute {
            if (isFetchFromDb()) {
                // reload from disk whatever we had
                result.addSource(loadFromDb()) { newData ->
                    setValue(Resource.success(newData))
                }
            } else {
                setValue(Resource.error(Constants.NO_RESPONSE, null))
            }
        }
    }

    private fun onApiError(dbSource: LiveData<ResultType>?, response: ApiErrorResponse<RequestType>) {
        onFetchFailed()
        if (dbSource == null || !isFetchFromDb()) {
            setValue(Resource.error(response.errorMessage, null))
        } else {
            result.addSource(dbSource) { newData ->
                setValue(Resource.error(response.errorMessage, newData))
            }
        }
    }


    protected open fun onFetchFailed() {
        // No implementation required here
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun isFetchFromDb(): Boolean

    @MainThread
    protected abstract fun getData(item: RequestType): ResultType

    @MainThread
    protected abstract fun isSaveInDb(): Boolean

    @MainThread
    protected abstract fun isFetchFromNetwork(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
