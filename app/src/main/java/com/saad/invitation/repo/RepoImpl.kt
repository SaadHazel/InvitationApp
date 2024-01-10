package com.saad.invitation.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.saad.invitation.api.MyApi
import com.saad.invitation.db.Appdb
import com.saad.invitation.models.Hit
import com.saad.invitation.utils.isInternetAvailable
import com.saad.invitation.utils.log

class RepoImpl(private val api: MyApi, private val db: Appdb, private val context: Context) : Repo {
    override val imagesLiveData = MutableLiveData<List<Hit>>()

    override suspend fun networkCheck() {
        if (!isInternetAvailable(context) || images.value != null) {
            doDatabaseCallGet();
        } else {
            doNetworkCall();
        }
    }

    override suspend fun doNetworkCall() {
        try {
            val response = api.getImages(2, 20, "wedding+invitation+card", "vertical")
            if (response.isSuccessful) {
                imagesLiveData.postValue(response.body()?.hits)
                response.body()?.hits?.forEach { hit ->
                    val count = excludeSameImage(hit.id)
                    log("Counter: $count")
                    if (count == 0) {
                        doDatabaseCallAdd(hit)
                    }
                }
            } else {
                // Handle unsuccessful response here
            }
        } catch (e: Exception) {
            // Handle network call exception here
        }
    }

    override suspend fun doDatabaseCallAdd(cards: Hit) {
        db.designDao().insertAll(cards)
    }

    override suspend fun doDatabaseCallGet() {
        imagesLiveData.postValue(db.designDao().getAll())

        /*   db.designDao().getAll().forEach { hit ->
               imagesLiveData.postValue(hit)
           }*/
    }

    override suspend fun excludeSameImage(id: Int): Int {
        return db.designDao().excludeSame(id)
    }
}