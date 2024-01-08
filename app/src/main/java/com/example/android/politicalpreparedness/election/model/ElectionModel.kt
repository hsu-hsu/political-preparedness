package com.example.android.politicalpreparedness.election.model

import android.os.Parcelable
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ElectionModel(
    val id: Int,
    val name: String,
    val electionDay: Date,
    val division: Division,
    val saved: Boolean
) : Parcelable

fun ElectionModel.toDataModel() =
    Election(
        id = id,
        name = name,
        electionDay = electionDay,
        division = division,
        saved = saved
    )
