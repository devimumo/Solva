package com.example.solva.SpareParts.View_Items_In_Cart

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.solva.R
import com.example.solva.Room_Database.db_instance.Items_added_to_cart_db_instance
import com.example.solva.SpareParts.Adapters.View_items_in_cart_from_local_roomdb_adapter
import com.example.solva.SpareParts.DataClasses.View_items_in_cart_data_class
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_spares__search__dashboard.*
import kotlinx.android.synthetic.main.items_in_cart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.util.ArrayList

class Items_in_cart : AppCompatActivity() {

    private val items_in_cart_arraylist = ArrayList< View_items_in_cart_data_class >()
private var grand_total_value=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.items_in_cart)
        
        retreive_data_from_room_database(this)
    }


    fun retreive_data_from_room_database(context: Context)
    {
        CoroutineScope(Dispatchers.IO).launch {
            var get_data= Items_added_to_cart_db_instance()
            var data_from_room_db=get_data.get_all_items_in_cart(context)

            var data_to_json= Gson()
            var data_json=data_to_json.toJson(data_from_room_db).toString()
            Log.d("mesu",data_json.toString())
            withContext(Dispatchers.Main) {
 set_to_recycler(context,data_json)
            }
        }
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


                var adap = View_items_in_cart_from_local_roomdb_adapter(items_in_cart_arraylist, context,grand_total_value.toString())

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