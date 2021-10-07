package com.example.solva.SpareParts.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.solva.R
import com.example.solva.SpareParts.Add_to_cart.Add_To_cart
import com.example.solva.SpareParts.DataClasses.Spare_parts_search_data_class
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout.view.*
import java.io.InputStream
import java.net.URL
import java.util.*


class Spare_search_tems_data_adapter(
    var spares_search_result: ArrayList<Spare_parts_search_data_class>,
    val c: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

   return Spare_search_tems_data_adapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var items_data: Spare_parts_search_data_class=spares_search_result[position]

        var holder=holder as ViewHolder


        holder.itemview.item_amount.text="kshs. "+items_data.item_amount
        holder.itemview.item_description.text=items_data.item_description
        holder.itemview.car_make.text=items_data.vehicle_make
        holder.itemview.car_model.text=" - "+items_data.vehicle_model

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
        var image_view=holder.itemView.imageView4

        val picasso = Picasso.get()
        picasso.load(image_url_value)
            .into(image_view)

holder.itemview.add_to_cart.setOnClickListener { view->

    val activity = view.getContext() as Activity

    var intent=Intent(view.context, Add_To_cart::class.java)


    var item_data_to_json_string= change_object_to_json_string(items_data)
    intent.putExtra("item_data_to_json_string",item_data_to_json_string)
    activity.startActivity(intent)
}

    }

    override fun getItemCount(): Int {
        return spares_search_result.size
    }

    class ViewHolder(var itemview: View):RecyclerView.ViewHolder(itemview) {

    }

}

fun LoadImageFromWebOperations(url: String?): Drawable? {
    return try {
        val `is`: InputStream = URL(url).getContent() as InputStream
        Drawable.createFromStream(`is`, "src name")
    } catch (e: Exception) {
        null
    }
}

private fun change_object_to_json_string(data: Spare_parts_search_data_class): String
{
    val item_data_to_json_string: String = Gson().toJson(data)

    return  item_data_to_json_string
}