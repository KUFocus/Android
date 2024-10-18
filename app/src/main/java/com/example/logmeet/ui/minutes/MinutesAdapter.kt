package com.example.logmeet.ui.minutes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.R
import com.example.logmeet.data.dto.minutes.MinutesListResult
import com.example.logmeet.databinding.ItemMinutesBinding
import com.example.logmeet.domain.entity.FileType
import com.example.logmeet.domain.entity.ProjectDrawableResources
import splitDateTime

class MinutesAdapter(private val data: List<MinutesListResult>) :
    RecyclerView.Adapter<MinutesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMinutesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MinutesListResult) {
            val number = item.color.split("_")[1].toInt()
            val color = ProjectDrawableResources.colorList[number - 1]
            binding.vMinutesColor.setBackgroundResource(color)

            var isManualType = true
            when (item.type) {
                FileType.MANUAL.type -> {
                    isManualType = true
                    binding.tvMinutesTitleN.text = item.minutesName
                }
                FileType.PICTURE.type -> {
                    isManualType = false
                    binding.tvMinutesTitleY.text = item.minutesName
                    binding.ivMinutes.setImageResource(R.drawable.ic_photo)
                }
                FileType.VOICE.type -> {
                    isManualType = false
                    binding.tvMinutesTitleY.text = item.minutesName
                    binding.ivMinutes.setImageResource(R.drawable.ic_video)
                }
            }
            binding.ivMinutes.visibility = if (isManualType) View.GONE else View.VISIBLE
            binding.tvMinutesTitleY.visibility = if (isManualType) View.GONE else View.VISIBLE
            binding.tvMinutesTitleN.visibility = if (isManualType) View.VISIBLE else View.GONE

            binding.tvMinutesDate.text = splitDateTime(item.createdAt).first

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, MinutesDetailActivity::class.java)
                intent.putExtra("projectId", item.projectId)
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