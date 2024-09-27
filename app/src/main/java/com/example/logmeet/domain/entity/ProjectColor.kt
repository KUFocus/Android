package com.example.logmeet.domain.entity

enum class ProjectColor(val type: String) {
    PROJECT_1("PROJECT_1"),
    PROJECT_2("PROJECT_2"),
    PROJECT_3("PROJECT_3"),
    PROJECT_4("PROJECT_4"),
    PROJECT_5("PROJECT_5"),
    PROJECT_6("PROJECT_6"),
    PROJECT_7("PROJECT_7"),
    PROJECT_8("PROJECT_8"),
    PROJECT_9("PROJECT_9"),
    PROJECT_10("PROJECT_10"),
    PROJECT_11("PROJECT_11"),
    PROJECT_12("PROJECT_12");

    companion object {
        fun fromString(type: String): ProjectColor? {
            return entries.find { it.type == type }
        }
    }
}