package com.example.logmeet.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.R
import com.example.logmeet.entity.ProjectData
import com.example.logmeet.databinding.ItemProjectHomeBinding

class HomeProjectAdapter(private val data: ArrayList<ProjectData>) :
    RecyclerView.Adapter<HomeProjectAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProjectHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProjectData) {
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
            binding.vPrjCircle.setBackgroundResource(color)
            binding.vPrjCircle2.setBackgroundResource(color)
            binding.tvPrjName.text = item.prjName
            binding.tvPrjDate.text = item.date
            binding.tvPrjPeople.text = item.people
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeProjectAdapter.ViewHolder {
        val binding = ItemProjectHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        // 첫 번째 아이템에만 마진을 적용
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        if (position == 0) {
            layoutParams.marginStart = 22.dpToPx(holder.itemView.context) // 12dp 마진 적용
        } else {
            layoutParams.marginStart = 0 // 다른 아이템들은 마진 없음
        }
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = data.size

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

}