package com.example.bus_scheduler_app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bus_scheduler_app.database.dao.ScheduleDao
import com.example.bus_scheduler_app.database.entities.Schedule

class BusScheduleViewModel(private val scheduleDao: ScheduleDao) : ViewModel() {
    fun fullSchedule(): List<Schedule> = scheduleDao.getAll()
    fun scheduleForStopName(name: String): List<Schedule> = scheduleDao.getByStopName(name)
}

class BusScheduleViewModelFactory(private val scheduleDao: ScheduleDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusScheduleViewModel(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}