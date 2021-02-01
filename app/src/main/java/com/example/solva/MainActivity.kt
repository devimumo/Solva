package com.example.solva

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import com.example.solva.SpareParts.Spares_Search_Dashboard
import java.io.Serializable

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    fun navigation_onclick(view: View) {

        when (view.id) {
            R.id.spare_parts -> {


                val intent = Intent(this, Spares_Search_Dashboard::class.java)
                val bundle = Bundle()
                startActivity(intent)
            }
        }
    }
}