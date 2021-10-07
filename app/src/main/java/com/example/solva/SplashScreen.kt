package com.example.solva

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)




        val actionBar = supportActionBar
        actionBar!!.hide()
        handler = Handler()
        handler.postDelayed({


            val MyPreferences = "mypref"
            val sharedPreferences =
                getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
            // String session_id= sharedPreferences.getString("sessions_ids","");


            //val profile_status = sharedPreferences.getString("profile_status", "!updated")
        /*    val registration_status = sharedPreferences.getString("registration_status", "!registered")
            confirm_account_using_otp =
                sharedPreferences.getString("account_regstration_pin", "!confirmed").toString()
            var profile_status=sharedPreferences.getString("profile_status", "!updated")
            var association_add_status=sharedPreferences.getString("association_add_status", "!updated")


            // Delay and Start Activity

            if (registration_status.equals("registered"))
            {

                if (confirm_account_using_otp.equals("!confirmed"))
                {
                    val intent = Intent(this, Confirm_account_activation_pin::class.java)
                    startActivity(intent)
                }
                else
                {

                    if (profile_status.equals("updated"))
                    {

                        if (association_add_status.equals("updated"))
                        {
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                        }
                        else{
                            val intent = Intent(this, Association_add::class.java)
                            startActivity(intent)
                        }

                    }
                    else{
                        val intent = Intent(this, Profile::class.java)
                        startActivity(intent)
                    }

                }




            }
            else
            {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }*/

            var start_manactivity_intent=Intent(this,MainActivity::class.java)
            startActivity(start_manactivity_intent)
            finish()
        } , 4500) // here we're delaying to startActivity after 3seconds
    }
}