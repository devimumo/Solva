package com.example.solva.Room_Database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity

@Dao
interface Items_added_to_cart_DAO {

    @Query("SELECT * FROM items_added_to_cart_table")
    fun getAll_values(): List<Items_added_to_cart_entity>

    @Insert
    fun insertAll(vararg message_payload: Items_added_to_cart_entity)

    @Query("Delete FROM items_added_to_cart_table")
    fun  delete()


    @Query("SELECT * FROM items_added_to_cart_table WHERE item_id LIKE :unique_id" )
    fun getAll(unique_id: String): List<Items_added_to_cart_entity>




    @Query("Update items_added_to_cart_table   set quantity = :quantity WHERE item_id= :item_id" )
    fun update_no_of_items_to_speicifc_cart_item(quantity: String, item_id: String)


}