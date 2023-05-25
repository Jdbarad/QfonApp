package com.builditcreative.qfonapp.data.remote

import com.builditcreative.qfonapp.data.local.PassengerEntity
import com.google.gson.annotations.SerializedName

data class ApiResultModel (
    @SerializedName("data") var data : List<Data> = emptyList() ,
    @SerializedName("pagination") var pagination : Pagination = Pagination()
) {
    fun asEntity() = data.map {
        it.asEntity()
    }
}

data class Airline (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("country") var country : String,
    @SerializedName("logo") var logo : String,
    @SerializedName("slogan") var slogan : String,
    @SerializedName("head_quaters") var headQuarters : String,
    @SerializedName("website") var website : String,
    @SerializedName("established") var established : String
)

data class Data (
    @SerializedName("_id") var id : String,
    @SerializedName("name") var name : String,
    @SerializedName("trips") var trips : Int,
    @SerializedName("airline") var airline : List<Airline>,
    @SerializedName("__v") var v : Int
) {
    fun asEntity() = PassengerEntity(
        id = id,
        name = name,
        trips = trips,
        airlineId = airline[0].id,
        airlineName = airline[0].name,
        airlineCountry = airline[0].country,
        airlineLogo = airline[0].logo,
        airlineSlogan = airline[0].slogan,
        airlineHeadQuarters = airline[0].headQuarters,
        airlineWebsite = airline[0].website,
        airlineEstablished = airline[0].established,
        v = v
    )
}

data class Links (
    @SerializedName("rel") var rel : String,
    @SerializedName("page") var page : Int,
    @SerializedName("href") var href : String
)

data class Pagination (
    @SerializedName("currentPage") var currentPage : Int = 0,
    @SerializedName("currentItems") var currentItems : Int = 0,
    @SerializedName("totalPages") var totalPages : Int = 0,
    @SerializedName("totalItems") var totalItems : Int = 0,
    @SerializedName("links") var links : List<Links> = emptyList()
)
