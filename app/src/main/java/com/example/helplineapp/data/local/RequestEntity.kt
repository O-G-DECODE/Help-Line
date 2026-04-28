package com.example.helplineapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class RequestStatus {
    OPEN,
    CLAIMED,
    COMPLETED
}

@Entity(tableName = "requests")
data class RequestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val requesterId: Long,
    val helperId: Long? = null,
    val description: String,
    val rewardAmount: Double,
    val location: String,
    val status: RequestStatus = RequestStatus.OPEN
)
