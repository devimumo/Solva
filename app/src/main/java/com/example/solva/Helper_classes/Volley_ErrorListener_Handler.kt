package com.example.solva.Helper_classes

import android.view.View
import com.android.volley.*
import com.google.android.material.snackbar.Snackbar


  class Volley_ErrorListener_Handler()
{
    fun check_error(it:VolleyError, view: View)
    {


        if (it is TimeoutError || it is NoConnectionError) {
            snack_bar(" No connection",view)



            //This indicates that the reuest has either time out or there is no connection
        } else if (it is AuthFailureError) {
            snack_bar(" No connection", view)


        } else if (it is ServerError) {
            snack_bar(" No connection", view)


        } else if (it is NetworkError) {
            snack_bar(" No connection", view)



        } else if (it is ParseError) {
            snack_bar(" No connection", view)



        }
    }

    fun snack_bar(error: String?, view: View) {
        val mysnackbar = Snackbar.make(view, error!!, Snackbar.LENGTH_LONG)
       mysnackbar.show()
    }


}

