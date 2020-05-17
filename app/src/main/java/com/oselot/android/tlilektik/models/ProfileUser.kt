package com.oselot.android.tlilektik.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profileuser_table")
class ProfileUser(@PrimaryKey @ColumnInfo(name = "username") val username: String,
                  @ColumnInfo(name = "name") val name: String,
                  @ColumnInfo(name = "lastname") val lastname: String,
                  @ColumnInfo(name = "description") val description: String
)