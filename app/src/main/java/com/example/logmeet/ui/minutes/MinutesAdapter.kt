package com.example.logmeet.ui.minutes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.R
import com.example.logmeet.entity.MinutesData
import com.example.logmeet.databinding.ItemMinutesBinding

class MinutesAdapter(private val data: ArrayList<MinutesData>) :
    RecyclerView.Adapter<MinutesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMinutesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MinutesData) {
            val colorList = arrayOf(
                R.drawable.color_prj1,
                R.drawable.color_prj2,
                R.drawable.color_prj3,
                R.drawable.color_prj4,
                R.drawable.color_prj5,
                R.drawable.color_prj6,
                R.drawable.color_prj7,
                R.drawable.color_prj8,
                R.drawable.color_prj9,
                R.drawable.color_prj10,
                R.drawable.color_prj11,
                R.drawable.color_prj12,
            )
            val color = colorList[item.prjColor.toInt()-1]
            binding.vMinutesColor.setBackgroundResource(color)

            val res = item.type == 0
            binding.ivMinutes.visibility = if (res) View.GONE else View.VISIBLE
            binding.tvMinutesTitleY.visibility = if (res) View.GONE else View.VISIBLE
            binding.tvMinutesTitleN.visibility = if (res) View.VISIBLE else View.GONE
            if (res) {
                binding.tvMinutesTitleN.text = item.title
            } else {
                binding.tvMinutesTitleY.text = item.title
            }
            binding.tvMinutesDate.text = item.date

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, MinutesDetailActivity::class.java)
                intent.putExtra("projectId", item.prjId)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMinutesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}