package com.saad.invitation.api

import com.saad.invitation.models.ImageList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("?key=41591099-498ce613b2bc24ef4a46c7fff")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("q") q: String,
        @Query("orientation") orientation: String,
    ): Response<ImageList>
}