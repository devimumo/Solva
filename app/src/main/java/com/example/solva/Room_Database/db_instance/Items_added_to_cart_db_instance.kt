package com.example.solva.Room_Database.db_instance

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.solva.Room_Database.App_database.Items_added_to_cart_DB
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity
import com.example.solva.SpareParts.Add_to_cart.Add_To_cart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
private var add_to_cart_instanse= Add_To_cart()

class Items_added_to_cart_db_instance {

    fun insert_item_payload(context: Context, items_payload: Items_added_to_cart_entity)
    {


        CoroutineScope(Dispatchers.IO).launch {

            val db = Room.databaseBuilder(context,
                    Items_added_to_cart_DB::class.java, "solva"
            ).build()

            db.Items_added_to_cart_DAO().insertAll(items_payload)



            withContext(Dispatchers.Main) {

                add_to_cart_instanse.change_visibility_of_items()
            }
        }

    }

    fun delete_specific_order_item(context: Context, unique_id: String)
    {


        CoroutineScope(Dispatchers.IO).launch {

            val db = Room.databaseBuilder(context,
                Items_added_to_cart_DB::class.java, "solva"
            ).build()

            db.Items_added_to_cart_DAO().delete_specific_order_item(unique_id)
        }

    }

    fun delete_all_order_items(context: Context)
    {


        CoroutineScope(Dispatchers.IO).launch {

            val db = Room.databaseBuilder(context,
                Items_added_to_cart_DB::class.java, "solva"
            ).build()

            db.Items_added_to_cart_DAO().delete_all_cart_items()
        }

    }


    fun add_quantity_of_items_in_cart(context: Context, unique_id: String, quantity_to_set: String)
    {

        CoroutineScope(Dispatchers.IO).launch {

            val db = Room.databaseBuilder(context, Items_added_to_cart_DB::class.java, "solva").build()

           db.Items_added_to_cart_DAO().update_no_of_items_to_speicifc_cart_item(quantity_to_set, unique_id)

        }


    }

   fun check_number_of_items_in_cart(context: Context): List<Items_added_to_cart_entity>
   {

       val db = Room.databaseBuilder( context, Items_added_to_cart_DB::class.java, "solva").build()
       var data= db.Items_added_to_cart_DAO().getAll_items_in_cart()

       return data

   }

    fun get_all_items_in_cart(context: Context): List<Items_added_to_cart_entity>
    {

        val db = Room.databaseBuilder( context, Items_added_to_cart_DB::class.java, "solva").build()
        var data= db.Items_added_to_cart_DAO().getAll_items_in_cart()

        return data

    }

    fun get_quanity_of_items_in_cart(context: Context,unique_id: String): String
    {

        val db = Room.databaseBuilder( context, Items_added_to_cart_DB::class.java, "solva").build()

        var data= db.Items_added_to_cart_DAO().get_quanity_of_items_in_cart(unique_id)


        return data
    }

    fun check_if_item_in_cart(context: Context,unique_id: String): List<Items_added_to_cart_entity>
    {

        val db = Room.databaseBuilder( context, Items_added_to_cart_DB::class.java, "solva").build()

        var data= db.Items_added_to_cart_DAO().getAll(unique_id)


        return data
    }


}