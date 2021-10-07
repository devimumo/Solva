package com.example.solva.View_model_items

import com.example.solva.Room_Database.DAO.Items_added_to_cart_DAO
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

 class Spares_Repository @Inject constructor (
  // private val webservice: Webservice,
    // Simple in-memory cache. Details omitted for brevity.
  //  private val executor: Executor,
    private val userDao: Items_added_to_cart_DAO
) {


    fun cart_items_from_roomdb(): Flow<List<Items_added_to_cart_entity>> {
        //refreshUser(userId)
        // Returns a Flow object directly from the database.
        return userDao.getAll_values()
    }

  /*  private suspend fun refreshUser(userId: String) {
        // Check if user data was fetched recently.
        val userExists = userDao.hasUser(FRESH_TIMEOUT)
        if (!userExists) {
            // Refreshes the data.
            val response = webservice.getUser(userId)

            // Check for errors here.

            // Updates the database. Since `userDao.load()` returns an object of
            // `Flow<User>`, a new `User` object is emitted every time there's a
            // change in the `User`  table.
            userDao.save(response.body()!!)
        }
    }*/

    companion object {

        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }
}