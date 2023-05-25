package com.builditcreative.qfonapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PassengerDao {

    @Query("SELECT * FROM PassengerEntity")
    fun getAllPassengers(): List<PassengerEntity>

    @Query("SELECT * FROM PassengerEntity WHERE id = :id")
    fun getPassenger(id: String): PassengerEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPassenger(passengerEntity: PassengerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPassengers(passengerEntity: List<PassengerEntity>)

    @Query("DELETE FROM PassengerEntity")
    fun deleteAllPassengers()

}