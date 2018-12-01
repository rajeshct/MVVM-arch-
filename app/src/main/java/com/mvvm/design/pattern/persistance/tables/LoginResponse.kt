package com.mvvm.design.pattern.persistance.tables

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class LoginResponse(
        @PrimaryKey(autoGenerate = true)
        var primaryKey: Int = 1,
        var userEmail: String
)