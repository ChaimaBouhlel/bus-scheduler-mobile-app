package com.example.bus_scheduler_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_scheduler_app.database.entities.Schedule
import com.example.bus_scheduler_app.databinding.ActivityMainBinding
import com.example.bus_scheduler_app.viewmodels.BusScheduleViewModel
import com.example.bus_scheduler_app.viewmodels.BusScheduleViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    private val viewModel: BusScheduleViewModel by viewModels {
        BusScheduleViewModelFactory((application as BusScheduleApplication).database.scheduleDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val busStopAdapter = BusStopAdapter(emptyList()) { schedule: Schedule ->
            println("Clicked on schedule: $schedule")
        }
        recyclerView.adapter = busStopAdapter

        var list: List<Schedule>
        Thread {
            // Background work
            list = viewModel.fullSchedule()
            // Post the result to the main thread using a Handler
            Handler(Looper.getMainLooper()).post {
                busStopAdapter.updateList(list)
            }
        }.start()
    }
}
