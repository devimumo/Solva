package com.example.solva.SpareParts.Add_to_cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.solva.R
import com.example.solva.SpareParts.DataClasses.Spare_parts_search_data_class
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add__to_cart.view.*
import kotlinx.android.synthetic.main.item_layout.view.*
private var rootView: View? = null


class Add_To_cart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__to_cart)

        rootView = window.decorView.rootView

        var intent=intent

        change_to_item_data(intent.getStringExtra("item_data_to_json_string")!!)



    }
}

private fun change_to_item_data(data:String): Spare_parts_search_data_class
{

    var data_fro_marker=Gson().fromJson<Spare_parts_search_data_class>(data,Spare_parts_search_data_class::class.java)
    set_to_view(data_fro_marker, rootView!!)
    //  Log.d("data_fro_marker",data_fro_marker.toString())
    return  data_fro_marker
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