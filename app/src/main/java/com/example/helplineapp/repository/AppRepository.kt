package com.example.helplineapp.repository

import com.example.helplineapp.data.local.AppDao
import com.example.helplineapp.data.local.RequestEntity
import com.example.helplineapp.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

class AppRepository(private val appDao: AppDao) {

    suspend fun registerUser(user: UserEntity): Long {
        return appDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): UserEntity? {
        return appDao.loginUser(email, password)
    }

    suspend fun getUserById(userId: Long): UserEntity? {
        return appDao.getUserById(userId)
    }

    suspend fun insertRequest(request: RequestEntity): Long {
        return appDao.insertRequest(request)
    }

    suspend fun updateRequest(request: RequestEntity) {
        appDao.updateRequest(request)
    }

    fun getAvailableRequests(currentUserId: Long): Flow<List<RequestEntity>> {
        return appDao.getAvailableRequests(currentUserId)
    }

    fun getRequestsByRequester(currentUserId: Long): Flow<List<RequestEntity>> {
        return appDao.getRequestsByRequester(currentUserId)
    }
}
