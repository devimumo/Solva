package com.example.solva.SpareParts.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.solva.R
import com.example.solva.Room_Database.db_instance.Items_added_to_cart_db_instance
import com.example.solva.SpareParts.DataClasses.Spare_parts_search_data_class
import com.example.solva.SpareParts.DataClasses.View_items_in_cart_data_class
import com.example.solva.SpareParts.Spares_Search_Dashboard
import com.example.solva.SpareParts.View_Items_In_Cart.Items_in_cart
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add__to_cart.view.*
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.item_layout.view.item_amount
import kotlinx.android.synthetic.main.item_layout.view.item_description
import kotlinx.android.synthetic.main.items_in_cart_recycler_layout.view.*
import kotlinx.android.synthetic.main.items_in_cart_recycler_layout.view.add_items_to_cart_sign
import kotlinx.android.synthetic.main.items_in_cart_recycler_layout.view.items_in_cart_quantity
import java.io.InputStream
import java.net.URL
import java.util.*

var quantity: String="0"

class View_items_in_cart_from_local_roomdb_adapter(
    var spares_search_result_from_local_room_db: ArrayList<View_items_in_cart_data_class>,val c: Context,var grand_total_value: String): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.items_in_cart_recycler_layout, parent, false)
   return View_items_in_cart_from_local_roomdb_adapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var items_data: View_items_in_cart_data_class=spares_search_result_from_local_room_db[position]

        var holder=holder as ViewHolder


        var items_n_cart_activity: Items_in_cart? = holder.itemView.context as Items_in_cart

        quantity=items_data.quantity

        holder.itemview.item_amount.text="kshs. "+items_data.item_amount
        holder.itemview.item_description.text=items_data.item_description
        holder.itemview.item_name.text=items_data.item_name
        holder.itemview.items_in_cart_quantity.text=items_data.quantity


        holder.itemview.remove_specific__item_from_cart.setOnClickListener {

     var new_total=(grand_total_value.toInt()-(holder.itemView.items_in_cart_quantity.text.toString().toInt()*items_data.item_amount.toInt())).toString()
           items_n_cart_activity!!.set_grand_total_amount_from_adapter(new_total )
            deleteItem(it,c,items_data.item_id,position,spares_search_result_from_local_room_db)

          //  Toast.makeText(items_n_cart_activity.applicationContext,items_data.quantity+"---"+items_data.item_amount,Toast.LENGTH_LONG).show()
            grand_total_value=new_total

            var update_no_in_cart=Spares_Search_Dashboard()
            update_no_in_cart.check_number_of_items_in_cart()

        }

        holder.itemview.reduce_no_of_items_button.setOnClickListener {


            var new_total=(grand_total_value.toInt()-(items_data.item_amount.toInt())).toString()
            items_n_cart_activity!!.set_grand_total_amount_from_adapter(new_total )
            grand_total_value=new_total


            var updated_quantity=holder.itemview.items_in_cart_quantity.text.toString()
            reduce_no_of_items_in_cart(c,updated_quantity,items_data.item_id,holder.itemView.items_in_cart_quantity)
          //  quantity=(quantity.toInt()-1).toString()

        }
        holder.itemview.add_items_to_cart_sign.setOnClickListener {

            var new_total=(grand_total_value.toInt()+(items_data.item_amount.toInt())).toString()
            items_n_cart_activity!!.set_grand_total_amount_from_adapter(new_total )
            grand_total_value=new_total


            var updated_quantity=holder.itemview.items_in_cart_quantity.text.toString()
            update_no_of_items_in_cart(c,updated_quantity,items_data.item_id,holder.itemView.items_in_cart_quantity)
          //  quantity=(quantity.toInt()+1).toString()


        }


        //////////////////
     /*   try {
           // val i: ImageView = findViewById(R.id.image) as ImageView
            val bitmap = BitmapFactory.decodeStream(URL(items_data.image_url).content as InputStream)
            holder.itemview.imageView4.setImageBitmap(bitmap)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }*/
        ///////////////////

        var image_url_value=items_data.image_url
        var image_view=holder.itemView.item_image_in_cart

        val picasso = Picasso.get()
       picasso.load(image_url_value).into(image_view)

    /* holder.itemview.add_to_cart.setOnClickListener { view->

    val activity = view.getContext() as Activity

    var intent=Intent(view.context, Add_To_cart::class.java)

    }*/

    }



    override fun getItemCount(): Int {
        return spares_search_result_from_local_room_db.size
    }

    class ViewHolder(var itemview: View):RecyclerView.ViewHolder(itemview) {

    }



    fun deleteItem(view: View,context: Context, unique_id: String,index: Int,item_data_arraylist: ArrayList<View_items_in_cart_data_class> ){

        var update_items= Items_added_to_cart_db_instance()


        update_items.delete_specific_order_item(context, unique_id)

        item_data_arraylist.removeAt(index)
        notifyItemRemoved(index)
        notifyDataSetChanged()

    }

}



private fun LoadImageFromWebOperations_function(url: String?): Drawable? {
    return try {
        val `is`: InputStream = URL(url).getContent() as InputStream
        Drawable.createFromStream(`is`, "src name")
    } catch (e: Exception) {
        null
    }
}

private fun update_no_of_items_in_cart ( context: Context,no_of_items: String,item_id: String,no_of_items_textview: TextView)
{

        var update_items= Items_added_to_cart_db_instance()


        var quantity_to_set= no_of_items.toInt()+1

        update_items.add_quantity_of_items_in_cart(context, item_id,quantity_to_set.toString())
    no_of_items_textview.text=quantity_to_set.toString()

}


private fun reduce_no_of_items_in_cart (
    context: Context,no_of_items: String,item_id: String,no_of_items_textview: TextView)
{

    var update_items= Items_added_to_cart_db_instance()


    var quantity_to_set= no_of_items.toInt()-1

    update_items.add_quantity_of_items_in_cart(context, item_id,quantity_to_set.toString())
    no_of_items_textview.text=quantity_to_set.toString()

}

private fun change_object_to_json_string(data: Spare_parts_search_data_class): String
{
    val item_data_to_json_string: String = Gson().toJson(data)

    return  item_data_to_json_string
}