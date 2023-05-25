package com.builditcreative.qfonapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PassengerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passengerDao(): PassengerDao
}
