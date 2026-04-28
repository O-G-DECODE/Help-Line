package com.example.helplineapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helplineapp.data.UserRole
import com.example.helplineapp.data.local.RequestEntity
import com.example.helplineapp.data.local.UserEntity
import com.example.helplineapp.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    // Simulating session management
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    // Dashboard Role Switch State
    private val _currentRole = MutableStateFlow(UserRole.REQUESTER)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    // Data for UI
    private val _availableRequests = MutableStateFlow<List<RequestEntity>>(emptyList())
    val availableRequests: StateFlow<List<RequestEntity>> = _availableRequests.asStateFlow()

    private val _myRequests = MutableStateFlow<List<RequestEntity>>(emptyList())
    val myRequests: StateFlow<List<RequestEntity>> = _myRequests.asStateFlow()

    fun switchRole(role: UserRole) {
        _currentRole.value = role
    }

    fun loginSimulated(userId: Long) {
        viewModelScope.launch {
            val user = repository.getUserById(userId)
            _currentUser.value = user
            
            // Once logged in, load data based on the user
            user?.let {
                loadRequestsData(it.id)
            }
        }
    }

    private fun loadRequestsData(userId: Long) {
        viewModelScope.launch {
            repository.getAvailableRequests(userId).collectLatest { requests ->
                _availableRequests.value = requests
            }
        }
        viewModelScope.launch {
            repository.getRequestsByRequester(userId).collectLatest { requests ->
                _myRequests.value = requests
            }
        }
    }

    fun postRequest(description: String, location: String, rewardAmount: Double) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val newRequest = RequestEntity(
                requesterId = user.id,
                description = description,
                rewardAmount = rewardAmount,
                location = location
            )
            repository.insertRequest(newRequest)
        }
    }
}
