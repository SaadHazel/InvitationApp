package com.saad.invitation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saad.invitation.models.Hit
import com.saad.invitation.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.doDatabaseCallGet()
            repo.networkCheck()
        }
    }

    val images: LiveData<List<Hit>>
        get() = repo.images

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
}