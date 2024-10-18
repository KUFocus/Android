package com.example.logmeet.domain.entity

enum class Status(val type: String) {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    TEMP("TEMP"),
    DELETED("DELETED")
}