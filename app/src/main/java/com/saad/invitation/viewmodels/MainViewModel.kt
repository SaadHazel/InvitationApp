package com.saad.invitation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saad.invitation.models.CardDesignModel
import com.saad.invitation.models.Hit
import com.saad.invitation.models.SingleCardItemsModel
import com.saad.invitation.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchAllDocumentsDataFromFireStore()
            repo.doDatabaseCallGet()
            repo.networkCheck()
        }
    }

    val images: LiveData<List<Hit>>
        get() = repo.images
    val designs: LiveData<List<CardDesignModel>>
        get() = repo.designs

    val singleCardDesign: LiveData<SingleCardItemsModel>
        get() = repo.singleCardItemsLiveData

    fun doDatabaseCallAdd(cards: Hit) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.doDatabaseCallAdd(cards)
        }
    }

    fun doDatabaseCallGet() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.doDatabaseCallGet()

        }
    }

    fun fetchSingleDocumentDataFromFireStore(document: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchSingleDocumentDataFromFireStore(document)
        }
    }
}