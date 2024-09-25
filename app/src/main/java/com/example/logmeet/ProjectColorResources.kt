package com.example.logmeet

import com.example.logmeet.entity.ProjectColor

class ProjectColorResources {
    companion object {
        val colorList = arrayOf(
            R.color.project1,
            R.color.project2,
            R.color.project3,
            R.color.project4,
            R.color.project5,
            R.color.project6,
            R.color.project7,
            R.color.project8,
            R.color.project9,
            R.color.project10,
            R.color.project11,
            R.color.project12
        )

        fun getColorResourceByProject(projectString: String): Int? {
            val projectColor = ProjectColor.fromString(projectString)

            return projectColor?.let {
                val index = it.ordinal  // enum의 순서를 가져옴 (0부터 시작)
                colorList.getOrNull(index)
            }
        }
    }
}