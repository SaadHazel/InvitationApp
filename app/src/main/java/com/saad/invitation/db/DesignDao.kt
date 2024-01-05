package com.saad.invitation.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saad.invitation.models.Hit

@Dao
interface DesignDao {


    @Insert
    fun insertAll(cards: Hit)

    @Query("SELECT * FROM cards")
    fun getAll(): List<Hit>
}