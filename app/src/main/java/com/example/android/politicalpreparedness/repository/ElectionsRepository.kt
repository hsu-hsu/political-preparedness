package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

class ElectionsRepository(private val database: ElectionDatabase) {

    val elections: LiveData<List<Election>> = database.electionDao.getAllElection()
    val voterInfo =  MutableLiveData<VoterInfoResponse>()
    val representatives =  MutableLiveData<RepresentativeResponse>()


    fun getElection(id: Int) = database.electionDao.getElectionById(id)

    suspend fun getVoterInfo(electionId: Int, address: String) {
        try {
            withContext(Dispatchers.IO) {
                val response = CivicsApi.retrofitService.getVoterInfoAsync(electionId, address).await()
                voterInfo.postValue(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insertElection(election: Election) {
        Timber.tag("election").i(election.saved.toString())
        withContext(Dispatchers.IO) {
            database.electionDao.insert(election)
        }
    }


    suspend fun refreshElections() {
        try {
            withContext(Dispatchers.IO) {
                val electionResponse = CivicsApi.retrofitService.getElectionsAsync().await()
                database.electionDao.insertAll(electionResponse.elections)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    val savedElections: LiveData<List<Election>> = database.electionDao.getSavedElections()
}