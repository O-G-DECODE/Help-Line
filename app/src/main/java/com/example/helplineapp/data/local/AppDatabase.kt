package com.example.helplineapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.helplineapp.data.UserRole

class Converters {
    @TypeConverter
    fun fromUserRole(role: UserRole): String {
        return role.name
    }

    @TypeConverter
    fun toUserRole(name: String): UserRole {
        return UserRole.valueOf(name)
    }

    @TypeConverter
    fun fromRequestStatus(status: RequestStatus): String {
        return status.name
    }

    @TypeConverter
    fun toRequestStatus(name: String): RequestStatus {
        return RequestStatus.valueOf(name)
    }
}

@Database(entities = [UserEntity::class, RequestEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
