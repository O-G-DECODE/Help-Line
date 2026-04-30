package com.example.helplineapp.ui

import androidx.lifecycle.ViewModel
import com.example.helplineapp.ui.models.Request
import com.example.helplineapp.ui.models.RequestStatus
import com.example.helplineapp.ui.models.ServiceType
import com.example.helplineapp.ui.models.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    // Simulating session management
    private val _currentUserId = MutableStateFlow<Long?>(null)

    // Dashboard Role Switch State (Will be mapped to Bottom Navigation)
    private val _currentRole = MutableStateFlow(UserRole.REQUESTER)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    // Data for UI
    private val _allRequests = MutableStateFlow<List<Request>>(getDummyRequests())
    val availableRequests: StateFlow<List<Request>> = _allRequests.asStateFlow()

    fun switchRole(role: UserRole) {
        _currentRole.value = role
    }

    fun loginSimulated(userId: Long) {
        _currentUserId.value = userId
    }

    fun postRequest(
        title: String,
        description: String,
        serviceType: ServiceType,
        pickupLocation: String,
        dropOffLocation: String,
        rewardAmount: Double,
        expiryTime: String
    ) {
        val userId = _currentUserId.value ?: 1L
        val newRequest = Request(
            id = System.currentTimeMillis(),
            requesterId = userId,
            title = title,
            description = description,
            serviceType = serviceType,
            pickupLocation = pickupLocation,
            dropOffLocation = dropOffLocation,
            rewardAmount = rewardAmount,
            expiryTime = expiryTime,
            status = RequestStatus.OPEN
        )
        
        // Add to in-memory list
        val updatedList = _allRequests.value.toMutableList()
        updatedList.add(0, newRequest) // Add to top
        _allRequests.value = updatedList
    }

    private fun getDummyRequests(): List<Request> {
        return listOf(
            Request(
                id = 1,
                requesterId = 2,
                title = "Pick up medication",
                description = "Need someone to pick up my BP meds. They are already paid for.",
                serviceType = ServiceType.PICK_AND_DROP,
                pickupLocation = "Aster Pharmacy - Thoppumpady",
                dropOffLocation = "Home",
                rewardAmount = 150.0,
                expiryTime = "1 Hour",
                status = RequestStatus.OPEN
            ),
            Request(
                id = 2,
                requesterId = 3,
                title = "Deliver medical files",
                description = "Deliver these files to the main reception.",
                serviceType = ServiceType.PICK_AND_DROP,
                pickupLocation = "Jacobs Hospital",
                dropOffLocation = "Kochi City Center",
                rewardAmount = 100.0,
                expiryTime = "3 Hours",
                status = RequestStatus.OPEN
            ),
            Request(
                id = 3,
                requesterId = 4,
                title = "Buy Groceries",
                description = "Need 2kg sugar and tea.",
                serviceType = ServiceType.BUY_AND_DELIVER,
                pickupLocation = "Local Store",
                dropOffLocation = "Home",
                rewardAmount = 50.0,
                expiryTime = "End of Day",
                status = RequestStatus.OPEN
            )
        )
    }
}
