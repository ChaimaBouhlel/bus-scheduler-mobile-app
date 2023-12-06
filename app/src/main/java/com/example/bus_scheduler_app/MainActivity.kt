package com.example.bus_scheduler_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_scheduler_app.database.AppDatabase
import com.example.bus_scheduler_app.database.dao.ScheduleDao
import com.example.bus_scheduler_app.database.entities.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var scheduleDao: ScheduleDao
    private lateinit var busStopAdapter: BusStopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize Dao
        scheduleDao = AppDatabase.getDatabase(this).scheduleDao()

        lifecycleScope.launch {
            val schedules: List<Schedule> = withContext(Dispatchers.IO) {
                scheduleDao.getAll()
            }

            // include recyclerView and adapter
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            busStopAdapter = BusStopAdapter(schedules)

            // Set up the RecyclerView with a LinearLayoutManager and adapter
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = busStopAdapter
            }
        }
    }
}