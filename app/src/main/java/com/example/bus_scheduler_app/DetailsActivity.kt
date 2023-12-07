package com.example.bus_scheduler_app

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bus_scheduler_app.databinding.ActivityDetailsBinding
import com.example.bus_scheduler_app.viewmodels.BusScheduleViewModel
import com.example.bus_scheduler_app.viewmodels.BusScheduleViewModelFactory
import java.util.Date

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: BusScheduleViewModel by viewModels {
        BusScheduleViewModelFactory((application as BusScheduleApplication).database.scheduleDao())
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stopName = intent.getStringExtra("stopName")

        val stationNameTextView: TextView = binding.stationNameTextView
        val arrivalTimesTextView: TextView = binding.arrivalTimesTextView

        viewModel.scheduleForStopName(stopName.orEmpty()).observe(this) {
            if (it.isNotEmpty()) {
                val schedule = it[0]
                val name = schedule.stopName
                stationNameTextView.text = "Station: $name"

                val time: Int = schedule.arrivalTime

                @SuppressLint("SimpleDateFormat")
                val formattedArrivalTimes =
                    SimpleDateFormat("h:mm a").format(Date(time.toLong() * 1000))

                arrivalTimesTextView.text = "Arrival Time: $formattedArrivalTimes"
            }
        }
    }
}
