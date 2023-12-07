package com.example.bus_scheduler_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_scheduler_app.databinding.ScheduleListFragmentBinding
import com.example.bus_scheduler_app.viewmodels.BusScheduleViewModel
import com.example.bus_scheduler_app.viewmodels.BusScheduleViewModelFactory

class ScheduleListFragment : Fragment(R.layout.schedule_list_fragment) {
    private var _binding: ScheduleListFragmentBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!


    private val viewModel: BusScheduleViewModel by viewModels {
        BusScheduleViewModelFactory((requireActivity().application as BusScheduleApplication).database.scheduleDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScheduleListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val busStopAdapter = BusStopAdapter(emptyList()) { schedule ->
            findNavController().navigate(ScheduleListFragmentDirections.actionScheduleListFragmentToDetailsFragment(schedule.stopName))
        }

        recyclerView.adapter = busStopAdapter

        viewModel.fullSchedule().observe(viewLifecycleOwner) {
            busStopAdapter.updateList(it)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}