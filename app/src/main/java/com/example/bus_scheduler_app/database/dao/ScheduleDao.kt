package com.example.bus_scheduler_app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.bus_scheduler_app.database.entities.Schedule

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM Schedule ORDER BY arrival_time ASC")
    fun getAll(): LiveData<List<Schedule>>

    @Query("SELECT * FROM Schedule WHERE stop_name = :stopName")
    fun getByStopName(stopName: String): LiveData<List<Schedule>>
}