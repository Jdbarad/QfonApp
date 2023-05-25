package com.builditcreative.qfonapp.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.builditcreative.qfonapp.data.remote.Airline
import com.builditcreative.qfonapp.data.remote.Data
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class PassengerEntity(
    @PrimaryKey var id: String,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("trips") var trips: Int,
    @ColumnInfo("airline_id") var airlineId: Int,
    @ColumnInfo("airline_name") var airlineName: String,
    @ColumnInfo("airline_country") var airlineCountry: String,
    @ColumnInfo("airline_logo") var airlineLogo: String,
    @ColumnInfo("airline_slogan") var airlineSlogan: String,
    @ColumnInfo("airline_head_quaters") var airlineHeadQuarters: String,
    @ColumnInfo("airline_website") var airlineWebsite: String,
    @ColumnInfo("airline_established") var airlineEstablished: String,
    @ColumnInfo("v") var v: Int
) : Parcelable {

    fun asData() : Data {
        return Data(
            id = id,
            name = name,
            trips = trips,
            airline = listOf(Airline(
                id = airlineId,
                name = airlineName,
                country = airlineCountry,
                logo = airlineLogo,
                slogan = airlineSlogan,
                headQuarters = airlineHeadQuarters,
                website = airlineWebsite,
                established = airlineEstablished
            )),
            v = v
        )
    }

}