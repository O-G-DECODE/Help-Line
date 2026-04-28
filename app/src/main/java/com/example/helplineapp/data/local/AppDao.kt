package com.example.helplineapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: RequestEntity): Long

    @Update
    suspend fun updateRequest(request: RequestEntity)

    // Helper View: List of available requests where requesterId != currentUserId
    @Query("SELECT * FROM requests WHERE requesterId != :currentUserId AND status = 'OPEN'")
    fun getAvailableRequests(currentUserId: Long): Flow<List<RequestEntity>>

    // Requester View: List of requests posted by the current user
    @Query("SELECT * FROM requests WHERE requesterId = :currentUserId")
    fun getRequestsByRequester(currentUserId: Long): Flow<List<RequestEntity>>
}
