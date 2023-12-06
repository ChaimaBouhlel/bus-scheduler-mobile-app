package com.example.bus_scheduler_app

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_scheduler_app.database.entities.Schedule
import java.util.Date

class BusStopAdapter(private var data: List<Schedule> = emptyList(), private val onItemClick: (Schedule) -> Unit = {}) :
    RecyclerView.Adapter<BusStopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bus_schedule_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusStopAdapter.ViewHolder, position: Int) {
        val schedule = data[position]
        holder.bind(schedule)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(schedule)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateList(schedules: List<Schedule>) {
        this.data = schedules
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textStopName: TextView
        private val textArrivalTime: TextView

        init {
            textStopName = itemView.findViewById(R.id.textStopName)
            textArrivalTime = itemView.findViewById(R.id.textArrivalTime)
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(schedule: Schedule) {
            textStopName.text = schedule.stopName
            textArrivalTime.text = SimpleDateFormat(
                "h:mm a"
            ).format(
                Date(schedule.arrivalTime.toLong() * 1000)
            )
        }
    }
}
