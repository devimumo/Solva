package com.example.solva.SpareParts.Add_to_cart

import android.content.Context
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity
import com.example.solva.Room_Database.db_instance.Items_added_to_cart_db_instance
import com.example.solva.SpareParts.DataClasses.Spare_parts_search_data_class
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private var add_to_cart_instanse=Add_To_cart()
private var db_instance=Items_added_to_cart_db_instance()

class Check_items_in_cart {
    fun check_if_item_in_cart_when_adding_to_cart(context: Context, unique_id: String,data_from_dashboard: Spare_parts_search_data_class)
    {
        CoroutineScope(Dispatchers.IO).launch {
            var get_items= Items_added_to_cart_db_instance()
            var items=get_items.check_if_item_in_cart(context,unique_id)

            var we=items.size



            if (we.equals(0)){
                withContext(Dispatchers.Main) {

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



        db_instance.insert_item_payload(context,items_to_cart_entity)





    }



}