package com.saad.invitation.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saad.invitation.models.Hit

@Database(entities = [Hit::class], version = 1)
abstract class Appdb : RoomDatabase() {
    abstract fun designDao(): DesignDao
}