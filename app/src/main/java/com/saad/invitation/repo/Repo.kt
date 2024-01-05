package com.saad.invitation.repo

import androidx.lifecycle.LiveData
import com.saad.invitation.models.Hit

interface Repo {

    val imagesLiveData: LiveData<List<Hit>>
    val images: LiveData<List<Hit>>
        get() = imagesLiveData

    suspend fun networkCheck()

    suspend fun doNetworkCall()

    suspend fun doDatabaseCallAdd(cards: Hit)

    suspend fun doDatabaseCallGet()

}