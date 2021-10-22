package com.example.solva.View_model_items

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.room.Room
import com.example.solva.R
import com.example.solva.Room_Database.App_database.Items_added_to_cart_DB
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity
import com.example.solva.SpareParts.Spares_Search_View_Model
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_spares__search__dashboard.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class View_Model_access_class {

    fun access_items_in_cart(
        viewmodestoreowner: ViewModelStoreOwner,
        context: Context,
        lifecycleOwner: LifecycleOwner,rootview: View
    )
    {
        val db = Room.databaseBuilder( context, Items_added_to_cart_DB::class.java, "solva").build()
        var ddg=db.Items_added_to_cart_DAO()

        //   val dao = Items_added_to_cart_DB.getInstance(application)?.Items_added_to_cart_DAO()
        val repository = ddg.let { Spares_Repository(it) }

        val factory = repository.let { Spares_Viewmodel_Factory(it) }

        var viewmodel: Spares_Search_View_Model
                = factory.let { ViewModelProvider(viewmodestoreowner, it).get(Spares_Search_View_Model::class.java) }

        viewmodel.cart_items.observe(lifecycleOwner, Observer{
            val badge = rootview.bottom_navigation.getOrCreateBadge(R.id.item_count_menu_item)



                //  var get_data= Items_added_to_cart_db_instance()
                //   var data_from_room_db=get_data.get_all_items_in_cart(context)

                if (it.isEmpty())
                {
                        //    set_to_recycler(this@Items_in_cart, data_json)
                        Toast.makeText(context,"Is empty", Toast.LENGTH_LONG).show()


                        //  getContentIfNotHandled(ddg,repository)

                }
                else {
                    badge.clearNumber()
                    badge.setVisible(true)
                    badge.number =it.size
                    Log.d("data_from_view_model", it.size.toString())

                    //  var data_to_json = Gson()
                    //   var data_json = data_to_json.toJson(it).toString()
                    // Log.d("mesu",data_json.toString())
                     var list_from_db=it

                    var data_to_json = Gson()
                    var data_json = data_to_json.toJson(it).toString()

                }


        })



    }
}