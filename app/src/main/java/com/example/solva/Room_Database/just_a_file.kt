package com.example.solva.Room_Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "Items_added_to_cart_table")
data class just_a_file (

    @PrimaryKey(autoGenerate = true)
    var chats_id:Int=0 ,

    @ColumnInfo(name= "item_id")
    var item_id: String="",

    @ColumnInfo(name= "item_description")
    var item_description: String="",

    @ColumnInfo(name= "item_amount")
    var item_amount: String="",
    //
    @ColumnInfo(name= "item_name")
    var item_name: String="",

    @ColumnInfo(name= "vehicle_make")
var vehicle_make: String="",

@ColumnInfo(name= "vehicle_model")
var vehicle_model: String="",

@ColumnInfo(name= "model_year")
var model_year: String="",

@ColumnInfo(name= "item_category")
var item_category: String="",

@ColumnInfo(name= "vendor_id")
var vendor_id: String="",

@ColumnInfo(name= "image_url")
var image_url: String="",

@ColumnInfo(name= "item_discount")
var item_discount: String="",

@ColumnInfo(name= "rating")
var rating: String=""




){

}