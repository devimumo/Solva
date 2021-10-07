package com.example.solva.SpareParts.Add_to_cart

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import com.example.solva.MainActivity
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity
import com.example.solva.Room_Database.db_instance.Items_added_to_cart_db_instance
import com.example.solva.SpareParts.DataClasses.Spare_parts_search_data_class
import com.example.solva.SpareParts.Spares_Search_Dashboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private var add_to_cart_instanse=Add_To_cart()
private var db_instance=Items_added_to_cart_db_instance()
private var update_no_in_cart= Spares_Search_Dashboard()


class Check_items_in_cart {
    fun check_if_item_in_cart_when_adding_to_cart(context: Context, unique_id: String,data_from_dashboard: Spare_parts_search_data_class)
    {
        CoroutineScope(Dispatchers.IO).launch {
            var get_items= Items_added_to_cart_db_instance()
            var items=get_items.check_if_item_in_cart(context,unique_id)

            var we=items.size

            if (we.equals(0)){
                withContext(Dispatchers.Main) {
                    var set_no_of_items_to_one_in_add_cart= Add_To_cart()



                    set_no_of_items_to_one_in_add_cart.set_number_of_items_text_to_one()
                 //   set_no_of_items_to_one_in_add_cart.check_number_of_items_in_cart_for_add_to_cart_activity()

                    add_to_cart_function(context, data_from_dashboard)
                }
            }
            else
            {

                withContext(Dispatchers.Main) {



                    add_to_cart_instanse.items_is_already_in_cart(context)
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




    fun add_to_cart_function(context: Context, dataFromDashboard: Spare_parts_search_data_class?) {


        var items_to_cart_entity= Items_added_to_cart_entity()

        items_to_cart_entity.item_id=dataFromDashboard!!.item_id
        items_to_cart_entity.image_url=dataFromDashboard!!.image_url
        items_to_cart_entity.item_amount=dataFromDashboard!!.item_amount
        items_to_cart_entity.item_category=dataFromDashboard!!.item_category
        items_to_cart_entity.item_description=dataFromDashboard!!.item_description
        items_to_cart_entity.item_discount=dataFromDashboard!!.item_discount
        items_to_cart_entity.item_name=dataFromDashboard!!.item_name
        items_to_cart_entity.model_year=dataFromDashboard!!.model_year
        items_to_cart_entity.quantity= 1.toString()
        items_to_cart_entity.rating=dataFromDashboard!!.rating
        items_to_cart_entity.vehicle_make=dataFromDashboard!!.vehicle_make
        items_to_cart_entity.vehicle_model=dataFromDashboard!!.vehicle_model
        items_to_cart_entity.vendor_id=dataFromDashboard!!.vendor_id

      var insert_return=  db_instance.insert_item_payload(context,items_to_cart_entity)



        add_to_cart_instanse.set_number_of_items_text_to_one()
        add_to_cart_instanse.check_number_of_items_in_cart_for_add_to_cart_activity()

    //    update_no_in_cart.check_number_of_items_in_cart()





    }



}