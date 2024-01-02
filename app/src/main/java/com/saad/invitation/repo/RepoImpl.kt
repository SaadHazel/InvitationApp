package com.saad.invitation.repo

import androidx.lifecycle.MutableLiveData
import com.saad.invitation.api.MyApi
import com.saad.invitation.models.ImageList

class RepoImpl(private val api: MyApi) : Repo {
    override val imagesLiveData = MutableLiveData<ImageList>()

    override suspend fun doNetworkCall() {
        try {
            val response = api.getImages(1, 20, "wedding+invitation+card", "vertical")
            if (response.isSuccessful) {
                imagesLiveData.postValue(response.body())
            } else {
                // Handle unsuccessful response here
            }
        } catch (e: Exception) {
            // Handle network call exception here
        }
    }
}