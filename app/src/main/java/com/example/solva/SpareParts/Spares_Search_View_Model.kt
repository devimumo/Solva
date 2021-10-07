package com.example.solva.SpareParts

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.solva.Room_Database.Entities.Items_added_to_cart_entity
import com.example.solva.View_model_items.Spares_Repository
import com.example.solva.View_model_items.Spares_Viewmodel_Factory
import javax.inject.Inject


class Spares_Search_View_Model
@Inject constructor(spares_repository: Spares_Repository): ViewModel() {
    // asLiveData() is part of lifecycle-livedata-ktx
    // https://developer.android.com/kotlin/ktx#livedata

    var cart_items: LiveData<List<Items_added_to_cart_entity>> =spares_repository.cart_items_from_roomdb().asLiveData()

   // var cart_items2: LiveData<List<Items_added_to_cart_entity>> =
}