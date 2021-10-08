package com.example.solva.SpareParts.View_Items_In_Cart

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.solva.R
import com.example.solva.Room_Database.App_database.Items_added_to_cart_DB
import com.example.solva.Room_Database.DAO.Items_added_to_cart_DAO
import com.example.solva.Room_Database.db_instance.Items_added_to_cart_db_instance
import com.example.solva.SpareParts.Adapters.View_items_in_cart_from_local_roomdb_adapter
import com.example.solva.SpareParts.DataClasses.View_items_in_cart_data_class
import com.example.solva.SpareParts.Spares_Search_Dashboard
import com.example.solva.SpareParts.Spares_Search_View_Model
import com.example.solva.View_model_items.Spares_Repository
import com.example.solva.View_model_items.Spares_Viewmodel_Factory

import com.google.gson.Gson
import kotlinx.android.synthetic.main.items_in_cart.*
import kotlinx.coroutines.*
import org.json.JSONArray
import java.util.ArrayList

class Items_in_cart : AppCompatActivity() {
    private var update_no_in_cart_for_spares_search_dashboard= Spares_Search_Dashboard()

    lateinit var adap:View_items_in_cart_from_local_roomdb_adapter;
    private val delete_function=Items_added_to_cart_db_instance()
    private val items_in_cart_arraylist = ArrayList< View_items_in_cart_data_class >()
    private var grand_total_value=0


   private lateinit var viewmodel: Spares_Search_View_Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.items_in_cart)

       // retreive_data_from_room_database(this)
        //Instantiate the database


        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
      //  model.currentName.observe(this, nameObserver)
      //  val factory = Spares_Viewmodel_Factory(Spares_Repository(dao))

        val db = Room.databaseBuilder( this, Items_added_to_cart_DB::class.java, "solva").build()
        var ddg=db.Items_added_to_cart_DAO()

        //   val dao = Items_added_to_cart_DB.getInstance(application)?.Items_added_to_cart_DAO()
        val repository = ddg.let { Spares_Repository(it) }

        val factory = repository.let { Spares_Viewmodel_Factory(it) }
        viewmodel = factory.let { ViewModelProvider(this, it).get(Spares_Search_View_Model::class.java) }

        viewmodel.cart_items.observe(this, Observer {


            CoroutineScope(Dispatchers.IO).launch {
                //  var get_data= Items_added_to_cart_db_instance()
                //   var data_from_room_db=get_data.get_all_items_in_cart(context)

                if (it.isEmpty())
                {
                    withContext(Dispatchers.Main) {
                        //    set_to_recycler(this@Items_in_cart, data_json)
                        Toast.makeText(applicationContext,"Is empty",Toast.LENGTH_LONG).show()


                        //  getContentIfNotHandled(ddg,repository)
                    }
                }
                else {

                    //  var data_to_json = Gson()
                    //   var data_json = data_to_json.toJson(it).toString()
                    // Log.d("mesu",data_json.toString())
                    Log.d("data_from_view_model", it.toString())

                    var data_to_json = Gson()
                    var data_json = data_to_json.toJson(it).toString()
                    withContext(Dispatchers.Main) {
                           set_to_recycler(this@Items_in_cart, data_json)


                    }
                }
            }


        })





        var message="Are you sure you want to clear cart. All items will be removed"

        cancel_order_button.setOnClickListener {

            remove_all_items_in_cart(this,message)
        }
    }




    fun getContentIfNotHandled(dao: Items_added_to_cart_DAO, repository: Spares_Repository)
    {

repository.cart_items_from_roomdb()

    }

    fun retreive_data_from_room_database(context: Context)
    {
        CoroutineScope(Dispatchers.IO).launch {
            var get_data = Items_added_to_cart_db_instance()
            var data_from_room_db = get_data.get_all_items_in_cart(context)


            if (data_from_room_db.isEmpty()) {

            } else {
                var data_to_json = Gson()
                var data_json = data_to_json.toJson(data_from_room_db).toString()
                Log.d("mesu", data_json.toString())
                withContext(Dispatchers.Main) {
                    set_to_recycler(context, data_json)
                }
            }
        }
    }

    private   fun remove_all_items_in_cart(context: Context, message: String) {

        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
                .setPositiveButton("Yes",
                        DialogInterface.OnClickListener { dialog, id ->
                            // super.onBackPressed()
                            delete_function.delete_all_order_items(context)
                            items_in_cart_arraylist.clear()
                            adap.notifyDataSetChanged()
                        //    update_no_in_cart_for_spares_search_dashboard.check_number_of_items_in_cart()
                            grand_total.text=""

                            CoroutineScope(Dispatchers.IO).launch {
                                delay(10000)
                            }
                            var intent= Intent(this,Spares_Search_Dashboard::class.java)
                            startActivity(intent)




                        })
                .setNegativeButton("No",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog

                        })
        // Create the AlertDialog object and return it
        builder.create()
        builder.show()
    }


    fun set_grand_total_amount_from_adapter(grand_total_amount: String)
    {

        grand_total.text=grand_total_amount
    }

    fun set_to_recycler(context: Context,data_json: String)
    {
        var dataJson_array= JSONArray(data_json)

        if (dataJson_array.length()!=0) {
            for (i in 0..dataJson_array.length() - 1) {

                Log.d("son_arra_",dataJson_array.length().toString())

                var data_at_position_jsonobect = dataJson_array.getJSONObject(i)
                var quantity= data_at_position_jsonobect.getString("quantity")
                    var item_amount_value= data_at_position_jsonobect.getString("item_amount")
var total_amount_for_item=quantity.toInt().toInt()*item_amount_value.toInt()


                var items_data_to_set_recycler = View_items_in_cart_data_class(
                    data_at_position_jsonobect.getString("item_id"),
                    data_at_position_jsonobect.getString("item_description"),
                    data_at_position_jsonobect.getString("item_amount"),
                    data_at_position_jsonobect.getString("item_name"),
                    data_at_position_jsonobect.getString("image_url"),
                    quantity

                    )


                grand_total_value=grand_total_value+total_amount_for_item


                adap = View_items_in_cart_from_local_roomdb_adapter(items_in_cart_arraylist, context,grand_total_value.toString())

                items_in_cart_arraylist.add(items_data_to_set_recycler)
                items_in_cart_recycler_view?.layoutManager = LinearLayoutManager(context)
                adap.notifyDataSetChanged()
                items_in_cart_recycler_view?.adapter = adap
                (items_in_cart_recycler_view?.layoutManager as LinearLayoutManager).setStackFromEnd(false)


                var mDividerItemDecoration_vertical = DividerItemDecoration(
                    items_in_cart_recycler_view.getContext(),
                    DividerItemDecoration.VERTICAL
                )
                items_in_cart_recycler_view.addItemDecoration(mDividerItemDecoration_vertical)

            }
            
            val grandtotal: TextView=findViewById(R.id.grand_total)
            grandtotal.text="kshs. "+grand_total_value.toString()

        }

    }

}