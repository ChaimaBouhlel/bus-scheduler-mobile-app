package com.example.bus_scheduler_app

import android.app.Application
import com.example.bus_scheduler_app.database.AppDatabase

class BusScheduleApplication : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}