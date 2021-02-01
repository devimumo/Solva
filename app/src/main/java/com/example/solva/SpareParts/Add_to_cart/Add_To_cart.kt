package com.example.solva.SpareParts.Add_to_cart

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.solva.R
import com.example.solva.Room_Database.db_instance.Items_added_to_cart_db_instance
import com.example.solva.SpareParts.DataClasses.Spare_parts_search_data_class
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add__to_cart.*
import kotlinx.android.synthetic.main.activity_add__to_cart.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

private var rootView: View? = null
private var quantity=0
private var  data_from_dashboard: Spare_parts_search_data_class? =null
private var check_if_item_in_cart_instanse=Check_items_in_cart()
private var add_to_cart_instanse=Add_To_cart()

class Add_To_cart : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__to_cart)

        rootView = window.decorView.rootView

        var intent=intent

        change_to_item_data(intent.getStringExtra("item_data_to_json_string")!!)

        add_items_to_cart_sign.setOnClickListener {

            add_items_to_cart_sign_function()
        }

        add_to_cart_layout.setOnClickListener {


          // add_to_cart_function(rootView,data_from_dashboard)
            check_if_item_in_cart_instanse.check_if_item_in_cart_when_adding_to_cart(rootView!!.context, data_from_dashboard!!.item_id, data_from_dashboard!!)
        }

    }

    private fun add_items_to_cart_sign_function() {

        var update_items= Items_added_to_cart_db_instance()


        var quantity_to_set= rootView!!.items_in_cart_quantity.text.toString().toInt()+1



        update_items.add_quantity_of_items_in_cart(rootView!!.context, data_from_dashboard!!.item_id,quantity_to_set.toString())
        rootView!!.items_in_cart_quantity.text=quantity_to_set.toString()
    }


    suspend  fun items_is_already_in_cart(context: Context) {


        Toast.makeText(rootView!!.context,"Item already in cart",Toast.LENGTH_LONG).show()

    }


    private fun change_to_item_data(data:String): String
    {

        data_from_dashboard=Gson().fromJson<Spare_parts_search_data_class>(data,Spare_parts_search_data_class::class.java)

        set_to_view(data_from_dashboard!!, rootView!!)

        check_if_item_in_cart_when_opening_this_window(rootView!!.context, data_from_dashboard!!.item_id, data_from_dashboard!!)

        return  data_from_dashboard!!.item_id
    }




    fun check_if_item_in_cart_when_opening_this_window(context: Context, unique_id: String,data_from_dashboard: Spare_parts_search_data_class)
    {
        CoroutineScope(Dispatchers.IO).launch {
            var get_items= Items_added_to_cart_db_instance()
            var items=get_items.check_if_item_in_cart(context,unique_id)


       //     Log.d("weee",items.toString())
           var siez_of_array=items.size

            if (siez_of_array.equals(0)){
                withContext(Dispatchers.Main) {

                    set_add_to_cart_as_visible()

                }
            }
            else
            {

                withContext(Dispatchers.Main) {

                    var dataa_to_json= Gson()
                    var data_to_json=dataa_to_json.toJson(items).toString()

                    Log.d("array_selected",data_to_json.toString())
                    var data_Json_array= JSONArray(data_to_json)


                    var message_at_position_jsonobect = data_Json_array.getJSONObject(0)

                    var quantity_selected=message_at_position_jsonobect.getString("quantity")

                    items_in_cart_quantity.text=quantity_selected.toString()
                    set_add_to_cart_as_invisible()

                }

            }

            /*  var dataa_to_json= Gson()
              var data_to_json=dataa_to_json.toJson(items).toString()
              Log.d("items_in_cart",data_to_json.toString()+"---"+we.toString())
              withContext(Dispatchers.Main) {
                  var instanse=Items_added_to_cart_db_instance()

               //   instanse.set_to_recycler(context, messages_json)
              }*/
        }
    }

   suspend fun  set_add_to_cart_as_visible() {

       rootView!!.add_to_cart_layout.visibility=View.VISIBLE
       rootView!!.add_to_cart_layout_add_or_remove_number_of_items.visibility=View.GONE
    }

    suspend fun  set_add_to_cart_as_invisible() {


        rootView!!.add_to_cart_layout.visibility=View.GONE
        rootView!!.add_to_cart_layout_add_or_remove_number_of_items.visibility=View.VISIBLE

    }


    private fun set_to_view(items_data: Spare_parts_search_data_class,view: View)
    {
        view.item_description_in_item_layout.text=items_data.item_description
        view.item_category_in_item_layout.text=items_data.item_category
        view.car_model_in_item_layout.text="-"+items_data.vehicle_model
        view.car_make_in_item_layout.text= items_data.vehicle_make
        view.discount_textview_in_item_layout.text="-"+items_data.item_discount+"%"

        var perc=100-items_data.item_discount.toInt()

        view.ratingBar2.rating= items_data.rating.toFloat()

        view.previous_amount_textview_in_item_layout.text="Kshs. "+((items_data.item_amount.toInt()*100)/perc).toString()

        view.item_amount_in_item_layout.text="Kshs. "+items_data.item_amount



        var image_url_value=items_data.image_url

        val picasso = Picasso.get()
        picasso.load(image_url_value)
                .into(view.imageView)


    }
}
 fun items_is_already_in_cart(context: Context)
{

}