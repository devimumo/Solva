package com.example.solva.Room_Database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface Items_added_to_cart_DAO {

    @Query("SELECT * FROM items_added_to_cart_table")
    fun  getDistinctUserById (): Flow<List<Items_added_to_cart_entity>>

    fun getAll_values ():
            Flow<List<Items_added_to_cart_entity>> = getDistinctUserById()
        .distinctUntilChanged()

    @Query("SELECT * FROM items_added_to_cart_table")
    fun getAll_values_2(): LiveData<List<Items_added_to_cart_entity>>

    @Insert
    fun insertAll( message_payload: Items_added_to_cart_entity): Long

    @Query("Delete FROM items_added_to_cart_table")
    fun  delete_all_cart_items()

    @Query("Delete FROM items_added_to_cart_table WHERE item_id LIKE :unique_id ")
    fun  delete_specific_order_item(unique_id: String)

    @Query("SELECT quantity FROM items_added_to_cart_table WHERE item_id LIKE :unique_id" )
    fun get_quanity_of_items_in_cart(unique_id: String): String


    @Query("SELECT * FROM items_added_to_cart_table WHERE item_id LIKE :unique_id" )
    fun getAll(unique_id: String): List<Items_added_to_cart_entity>

    @Query("SELECT * FROM items_added_to_cart_table " )
    fun getAll_items_in_cart(): List<Items_added_to_cart_entity>



    @Query("Update items_added_to_cart_table   set quantity = :quantity WHERE item_id= :item_id" )
    fun update_no_of_items_to_speicifc_cart_item(quantity: String, item_id: String): Int


}