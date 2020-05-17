package com.oselot.android.tlilektik.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProfileUser::class], version = 1, exportSchema = false)
abstract class ProfileUserRoomDatabase : RoomDatabase() {

    abstract fun profileUserDao(): ProfileUserDao

    companion object {

        @Volatile
        private var INSTANCE: ProfileUserRoomDatabase? = null

        fun getDatabase(context: Context): ProfileUserRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProfileUserRoomDatabase::class.java,
                        "profileuser_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}