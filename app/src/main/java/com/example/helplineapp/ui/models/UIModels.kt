package com.example.helplineapp.ui.models

enum class UserRole {
    REQUESTER,
    HELPER
}

enum class RequestStatus {
    OPEN,
    CLAIMED,
    COMPLETED
}

enum class ServiceType {
    BUY_AND_DELIVER,
    PICK_AND_DROP
}

data class Request(
    val id: Long = 0,
    val requesterId: Long,
    val helperId: Long? = null,
    val title: String,
    val description: String,
    val rewardAmount: Double,
    val pickupLocation: String,
    val dropOffLocation: String,
    val serviceType: ServiceType = ServiceType.BUY_AND_DELIVER,
    val expiryTime: String = "End of Day",
    val status: RequestStatus = RequestStatus.OPEN
)
