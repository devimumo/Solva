package com.example.solva.Room_Database.App_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.solva.Room_Database.DAO.Items_added_to_cart_DAO
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity

@Database(version=1, entities =  arrayOf(Items_added_to_cart_entity::class) )


abstract class Items_added_to_cart_DB: RoomDatabase() {

    abstract fun Items_added_to_cart_DAO (): Items_added_to_cart_DAO


}