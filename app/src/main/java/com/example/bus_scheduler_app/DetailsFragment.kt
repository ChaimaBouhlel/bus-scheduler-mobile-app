package com.example.bus_scheduler_app

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bus_scheduler_app.databinding.DetailsFragmentBinding
import com.example.bus_scheduler_app.viewmodels.BusScheduleViewModel
import com.example.bus_scheduler_app.viewmodels.BusScheduleViewModelFactory
import java.util.Date

class DetailsFragment : Fragment(){
    private lateinit var  binding : DetailsFragmentBinding
    private lateinit var stopName: String
    private val viewModel: BusScheduleViewModel by viewModels {
        BusScheduleViewModelFactory((requireActivity().application as BusScheduleApplication).database.scheduleDao())
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val stationNameTextView: TextView = binding.stationNameTextView
        val arrivalTimesTextView: TextView = binding.arrivalTimesTextView

        arguments.let{
            stopName = it?.getString("stopName").toString()
        }

        viewModel.scheduleForStopName(stopName).observe(viewLifecycleOwner) {
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

        return view
    }

}