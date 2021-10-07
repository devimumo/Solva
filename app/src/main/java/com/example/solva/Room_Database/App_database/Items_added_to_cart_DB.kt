package com.example.solva.Room_Database.App_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.solva.Room_Database.DAO.Items_added_to_cart_DAO
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity

@Database(version=1, entities =  arrayOf(Items_added_to_cart_entity::class) )


abstract class Items_added_to_cart_DB: RoomDatabase() {

    abstract fun Items_added_to_cart_DAO (): Items_added_to_cart_DAO


    companion object {
        private var INSTANCE: Items_added_to_cart_DB? = null

        fun getInstance(context: Context): Items_added_to_cart_DB? {
            if (INSTANCE == null) {
                synchronized(Items_added_to_cart_DB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, Items_added_to_cart_DB::class.java, "user.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}