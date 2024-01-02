package com.saad.invitation.repo

import androidx.lifecycle.LiveData

import com.saad.invitation.models.ImageList

interface Repo {

    val imagesLiveData: LiveData<ImageList>
    val images: LiveData<ImageList>
        get() = imagesLiveData

    suspend fun doNetworkCall()

}