package com.saad.invitation.di


import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.saad.invitation.api.MyApi
import com.saad.invitation.db.Appdb
import com.saad.invitation.repo.Repo
import com.saad.invitation.repo.RepoImpl
import com.saad.invitation.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { FirebaseFirestore.getInstance() }

    single {
        Room.databaseBuilder(
            androidContext(),
            Appdb::class.java, "designs"
        ).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }


    single<Repo> { RepoImpl(get(), get(), androidContext(), get()) }

    viewModel { MainViewModel(get()) }

}