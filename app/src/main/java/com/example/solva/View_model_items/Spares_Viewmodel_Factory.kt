package com.example.solva.View_model_items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.solva.SpareParts.Spares_Search_View_Model

class Spares_Viewmodel_Factory(
    private val repository: Spares_Repository
): ViewModelProvider.Factory {



    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Spares_Search_View_Model::class.java)) {
            return Spares_Search_View_Model(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
        }

    }
