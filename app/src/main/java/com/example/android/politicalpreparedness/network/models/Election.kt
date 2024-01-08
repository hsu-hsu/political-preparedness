package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import androidx.room.*
import com.example.android.politicalpreparedness.election.model.ElectionModel
import com.squareup.moshi.*
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "election_table")
@Parcelize
data class Election(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "name")val name: String,
        @ColumnInfo(name = "electionDay")val electionDay: Date,
        @Embedded(prefix = "division_") @Json(name="ocdDivisionId") val division: Division,
        @ColumnInfo(name = "saved") var saved: Boolean = false
): Parcelable

//fun List<Election>.toDomainModel(): List<ElectionModel> {
//        return map {
//                ElectionModel(
//                        it.id,
//                        it.name,
//                        it.electionDay,
//                        it.division,
//                        it.saved
//                )
//        }
//}