package com.saad.invitation.repo

import androidx.lifecycle.LiveData
import com.saad.invitation.models.CardDesignModel
import com.saad.invitation.models.Hit
import com.saad.invitation.models.SingleCardItemsModel

interface Repo {

    val _loading: LiveData<Boolean>
    val loading: LiveData<Boolean>
        get() = _loading

    val imagesLiveData: LiveData<List<Hit>>
    val images: LiveData<List<Hit>>
        get() = imagesLiveData


    val cardDesignLiveData: LiveData<List<CardDesignModel>>
    val designs: LiveData<List<CardDesignModel>>
        get() = cardDesignLiveData


    val singleCardItemsLiveData: LiveData<SingleCardItemsModel>
    val singleDesigns: LiveData<SingleCardItemsModel>
        get() = singleCardItemsLiveData


    suspend fun networkCheck()

    suspend fun doNetworkCall()

    suspend fun doDatabaseCallAdd(cards: Hit)

    suspend fun doDatabaseCallGet()

    suspend fun excludeSameImage(id: Int): Int

    suspend fun fetchSingleDocumentDataFromFireStore(documentId: String)

    suspend fun fetchAllDocumentsDataFromFireStore()

}