package com.example.bus_scheduler_app.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "stop_name") val stopName: String?,
    @ColumnInfo(name = "arrival_time") val arrivalTime: String?
)