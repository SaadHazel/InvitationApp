package com.saad.invitation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saad.invitation.models.ImageList
import com.saad.invitation.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.doNetworkCall()
        }
    }

    val images: LiveData<ImageList>
        get() = repo.images

}