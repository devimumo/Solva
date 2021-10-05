package com.example.solva

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import com.example.solva.Repair_Car.Repair_Car
import com.example.solva.SpareParts.Spares_Search_Dashboard
import com.example.solva.Vendor.AddGarage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

        exit_back_button.setOnClickListener {

            close_to_exit()
        }
    }

    override fun onBackPressed() {
    close_to_exit()
    }
      private   fun close_to_exit() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want exit")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    // super.onBackPressed()

                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra("EXIT", true)
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

    fun navigation_onclick(view: View) {

        when (view.id) {
            R.id.spare_parts -> {


                val intent = Intent(this, Spares_Search_Dashboard::class.java)
                val bundle = Bundle()
                startActivity(intent)
            }

            R.id.repair_car_map_view->{
                val _repair_carintent = Intent(this, Repair_Car::class.java)
                startActivity(_repair_carintent)
            }

            R.id.add_garage->{
                val _repair_carintent = Intent(this, AddGarage::class.java)
                startActivity(_repair_carintent)
            }


        }
    }
}