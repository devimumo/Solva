package com.example.solva.SpareParts

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.solva.Helper_classes.Volley_ErrorListener_Handler
import com.example.solva.R
import com.example.solva.Room_Database.db_instance.Items_added_to_cart_db_instance
import com.example.solva.SpareParts.Adapters.Spare_search_tems_data_adapter
import com.example.solva.SpareParts.DataClasses.Spare_parts_search_data_class
import com.example.solva.SpareParts.View_Items_In_Cart.Items_in_cart
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add__to_cart.*
import kotlinx.android.synthetic.main.activity_spares__search__dashboard.*
import kotlinx.android.synthetic.main.activity_spares__search__dashboard.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

private var rootView: View? = null
private val spares_arraylist = ArrayList<Spare_parts_search_data_class>()
private var recycler_view: RecyclerView? =null
//private var vehicle_make_arraylist: List<String>? =null
private var vehicle_model_arraylist: ArrayList<String>? =null
private var model_year_arraylist: ArrayList<String>? =null
private var vehicle_category: List<String>? =null
private var spinner: Spinner ?= null
private var search_parameter_value = "empty_data"


private var search_parameters_vehicle_make: String="Toyota";
private var search_parameters_vehicle_model="modelll";
private var search_parameters_model_year="1222";
private var search_parameters_category= "empty_data"
private var modely_array_list: Array<String>?=null
private var search_mode="";





class Spares_Search_Dashboard : Activity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_spares__search__dashboard)
recycler_view= recycler_view
       rootView = window.decorView.rootView


        check_number_of_items_in_cart()


bottom_navigation.setOnNavigationItemSelectedListener {
    when(it.itemId){
        R.id.item_count_menu_item->{

            val intent=Intent(this,Items_in_cart::class.java)
            startActivity(intent)

        }

        else -> {

        }
    }

    true
}


shop_now.setOnClickListener {

    searchview.setIconified(true); // close the search editor and make search icon again


    if (textView.text.toString().isEmpty())
    {
        search_parameter_value="empty_data"
    }
    else
    {
        search_parameter_value=textView.text.toString()

    }
    get_items_searched_data_from_database("", rootView!!, "all")
}

        var vehicle_make_arraylist= arrayOf("Toyota","Ford","Chevloret")
        var item_category_arraylist= arrayOf("","lights","gear","tires","seats")
        var vehicle_mmodel_ford_arraylist= arrayOf("figo","Everest","aspire","freestyle")
        var vehicle_model_chevloret_arraylist= arrayOf("blazer","bolt","camara","colorado")

        var mode_year_arraylist= arrayOf("1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010",
            "2011", "2012","2013","2014","2015","2016","2017","2018","2019","2020")

        //  spinner = this.vehicle_make_spinner()
        vehicle_make_spinner!!.setOnItemSelectedListener(this)

        vehicle_model_spinner!!.setOnItemSelectedListener(this)

        model_year_spinner!!.setOnItemSelectedListener(this)

        part_category_spinner!!.setOnItemSelectedListener(this)


        vehicle_make_spinner!!.onItemSelectedListener(this,vehicle_make_arraylist,vehicle_make_spinner)



        model_year_spinner!!.onItemSelectedListener(this,mode_year_arraylist,model_year_spinner)
        part_category_spinner!!.onItemSelectedListener(this,item_category_arraylist,part_category_spinner)




        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView: SearchView = searchview as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                search_parameter_value=query

                 //   get_members_details_progress_bar.visibility=View.VISIBLE
                    get_items_searched_data_from_database(search_parameter_value!!, rootView!!,"general")



                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                  //  recycler_view.visibility = View.GONE
                } else {

                    //  fetch_details_volley(newText)

                }

                return false
            }
        })


        // Create an ArrayAdapter using a simple spinner layout and languages array

    }

    fun check_number_of_items_in_cart()
    {

        var context= rootView!!.context
        CoroutineScope(Dispatchers.IO).launch {
            var get_items= Items_added_to_cart_db_instance()
            var items=get_items.check_number_of_items_in_cart(context)

            //     Log.d("weee",items.toString())
            var size_of_array=items.size

            if (size_of_array.equals(0)){
                withContext(Dispatchers.Main) {

                    //An icon only badge will be displayed unless a number is set:

                    val badgeDrawable = rootView!!.bottom_navigation.getBadge(R.id.item_count_menu_item)
                    if (badgeDrawable != null) {
                        badgeDrawable.isVisible = false
                        badgeDrawable.clearNumber()
                    }
                }
            }
            else
            {
                withContext(Dispatchers.Main) {

                    val badge = rootView!!.bottom_navigation.getOrCreateBadge(R.id.item_count_menu_item)
                    badge.isVisible = true

                    badge.number=size_of_array
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

    private fun get_items_searched_data_from_database(search_query: String, view: View, search_mode: String)
    {
        constraintLayout.visibility=View.GONE
        show_filters_pane_layout.visibility=View.VISIBLE
        view.spares_search_progress_bar.visibility=View.VISIBLE

        recycler_view=items_recycler_view

          spares_arraylist.clear()

        val requestQueue = Volley.newRequestQueue(this)

        //var get_items_searched_data_from_database_url="https://daudi.azurewebsites.net/solva/retreive_item_data.php?";
        var get_items_searched_data_from_database_url="https://project-daudi.000webhostapp.com/solva/retreive_item_data.php?";

        val stringRequest: StringRequest=object : StringRequest(Method.POST,
            get_items_searched_data_from_database_url,
            Response.Listener { response ->

                try {
                    search_parameters_category="empty_data"
                    search_parameter_value="empty_data"

                    Log.d("response_data", response)

                    var jsonObject = JSONObject(response)
               var status=jsonObject.getString("response")

                    if (status.equals("data!"))
                    {
                        constraintLayout.visibility=View.VISIBLE
                        show_filters_pane_layout.visibility=View.GONE

                        spares_search_progress_bar.visibility=View.GONE
                          snack_bar("Search result empty", rootView!!)
                    }
                    else {
                        var data1 = jsonObject.getJSONObject("items_data")
                        var data = data1.getJSONArray("items_data")

                        for (i in 0..data.length() - 1) {

                            var data_value_at_i = data.getJSONObject(i)

                            var item_data_for_dataclass = Spare_parts_search_data_class(

                                data_value_at_i.getString("item_id"),
                                data_value_at_i.getString("item_description"),
                                data_value_at_i.getString("item_amount"),
                                data_value_at_i.getString("item_name"),
                                data_value_at_i.getString("vehicle_make"),
                                data_value_at_i.getString("vehicle_model"),
                                data_value_at_i.getString("model_year"),
                                data_value_at_i.getString("item_category"),
                                data_value_at_i.getString("no_of_items_in_stock"),
                                data_value_at_i.getString("vendor_id"),
                                data_value_at_i.getString("image_url"),
                                data_value_at_i.getString("item_discount"),
                                data_value_at_i.getString("rating")
                            )

                            var adap = Spare_search_tems_data_adapter(
                                spares_arraylist,
                                this
                            )
                            spares_arraylist.add(item_data_for_dataclass)

                            recycler_view?.layoutManager = GridLayoutManager(this, 2)
                            adap.notifyDataSetChanged()

                            spares_search_progress_bar.visibility = View.GONE

                            var mDividerItemDecoration = DividerItemDecoration(
                                items_recycler_view.getContext(),
                                DividerItemDecoration.HORIZONTAL
                            )
                            var mDividerItemDecoration_vertical = DividerItemDecoration(
                                items_recycler_view.getContext(),
                                DividerItemDecoration.VERTICAL
                            )
                            items_recycler_view.addItemDecoration(mDividerItemDecoration)
                            //items_recycler_view.addItemDecoration(mDividerItemDecoration_vertical)
                            //addItemDecoration(   DividerItemDecoration( this, DividerItemDecoration.VERTICAL)


                            recycler_view?.adapter = adap
                        }
                    }

                        //   (recycler_view?.layoutManager as LinearLayoutManager).setStackFromEnd(true)

                    //   Log.d("response_data",response)
                }
                catch (e: JSONException) {


                    snack_bar("Search error.Try again",view)

                    search_parameters_category="empty_data"
                    search_parameter_value="empty_data"


                    constraintLayout.visibility=View.VISIBLE
                    show_filters_pane_layout.visibility=View.GONE

                    spares_search_progress_bar.visibility=View.GONE


                    Log.d("JSONE", e.toString())

                }

            }, Response.ErrorListener {


                var instanse= Volley_ErrorListener_Handler()
                instanse.check_error(it,view)

                search_parameters_category="empty_data"
                search_parameter_value="empty_data"


                constraintLayout.visibility=View.VISIBLE
                show_filters_pane_layout.visibility=View.GONE
                spares_search_progress_bar.visibility=View.GONE

                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

                Log.d("response_data_error", it.toString())


            })
        {


            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()

                params["search_parameters"] = search_parameter_value
                params["phone_number"] =  "David"
                params["search_parameters_vehicle_make"] = search_parameters_vehicle_make.toString()
                params["search_parameters_vehicle_model"] = search_parameters_vehicle_model.toString()
                params["search_parameters_model_year"] = search_parameters_model_year.toString()
                params["search_parameters_category"] = search_parameters_category.toString()
                params["search_mode"] = search_mode


                return params
            }


        }

        requestQueue.add(stringRequest)


    }


     fun set_to_recycler(context: Context, messagesJson: String, view: View) {


      recycler_view=items_recycler_view

        recycler_view?.layoutManager = LinearLayoutManager(context)
        (recycler_view?.layoutManager as LinearLayoutManager).setStackFromEnd(true)


        var messagesJson_array= JSONArray(messagesJson)


         spares_arraylist.clear()
        if (messagesJson_array.length()!=0) {
            for (i in 0..messagesJson_array.length() - 1) {


                var message_at_position_jsonobect = messagesJson_array.getJSONObject(i)


            /*    var chats_data = Spare_parts_search_data_class(
                    message_at_position_jsonobect.getString("username"),
                    message_at_position_jsonobect.getString("from_id_no")
                )*/


                var adap = Spare_search_tems_data_adapter(spares_arraylist, context)

             //   spares_arraylist.add(chats_data)
                recycler_view?.layoutManager = LinearLayoutManager(context)
                adap.notifyDataSetChanged()
                recycler_view?.adapter = adap
                (recycler_view?.layoutManager as LinearLayoutManager).setStackFromEnd(true)

            }

        }
        else
        {

            //
        }

    }

    fun onclick(view: View) {

        when (view.id) {

            R.id.show_filters_pane_layout->{

                constraintLayout.visibility=View.VISIBLE
                show_filters_pane_layout.visibility=View.GONE

            }

            R.id.panesss->{

                constraintLayout.visibility=View.GONE
                show_filters_pane_layout.visibility=View.VISIBLE
            }
        }
        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var vehicle_model_toyota_arraylist= arrayOf("Axio","Premio","Vitz","Fielder")
        var vehicle_mmodel_ford_arraylist= arrayOf("figo","Everest","aspire","freestyle")
        var vehicle_model_chevloret_arraylist= arrayOf("blazer","bolt","camara","colorado")

        if(parent?.getId() == R.id.vehicle_make_spinner)

        {
            search_parameters_vehicle_make= (parent.getItemAtPosition(position).toString())

            if (search_parameters_vehicle_make.equals("Ford"))
            {


                vehicle_model_spinner!!.onItemSelectedListener(this,vehicle_mmodel_ford_arraylist,vehicle_model_spinner)

            }
            else if(search_parameters_vehicle_make.equals("Toyota"))
            {



                vehicle_model_spinner!!.onItemSelectedListener(this,vehicle_model_toyota_arraylist,vehicle_model_spinner)
            }
            else if(search_parameters_vehicle_make.equals("Chevloret"))
            {

                vehicle_model_spinner!!.onItemSelectedListener(this,vehicle_model_chevloret_arraylist,vehicle_model_spinner)

            }


            Log.d("selected_value", search_parameters_vehicle_make)

        }
        else if (parent?.getId() == R.id.vehicle_model_spinner)
        {
            search_parameters_vehicle_model= (parent.getItemAtPosition(position).toString())
            Log.d("selected_value", search_parameters_vehicle_model)

        }

        else if (parent?.getId() == R.id.model_year_spinner)
        {
            search_parameters_model_year= (parent.getItemAtPosition(position).toString())
            Log.d("selected_value", search_parameters_model_year)

        }
        else if (parent?.getId() == R.id.part_category_spinner)
        {

            search_parameters_category= (parent?.getItemAtPosition(position).toString())
            Log.d("selected_value", search_parameters_category)

        }




    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }


}


private fun DialogInterface.OnClickListener.onItemSelectedListener(
    sparesSearchDashboard: Spares_Search_Dashboard,
    arraylist: Array<String>,spinner_to_use: Spinner
) {


    val aa_1 = ArrayAdapter(sparesSearchDashboard, android.R.layout.simple_spinner_item, arraylist)
    // Set layout to use when the list of choices appear
    aa_1.setDropDownViewResource(android.R.layout.simple_list_item_1)
    // Set Adapter to Spinner
    spinner_to_use!!.setAdapter(aa_1)

}



private fun snack_bar(error: String?, view: View) {
    val mysnackbar = Snackbar.make(view, error!!, Snackbar.LENGTH_LONG)
    mysnackbar.show()
}




