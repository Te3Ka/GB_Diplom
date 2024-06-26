package ru.te3ka.boardgamerdiary.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkContact
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkMyCollection
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkProfile
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWantToPlay
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWishlist

interface ApiService {
    @POST("upload/profile")
    fun uploadProfile(@Body networkProfile: NetworkProfile): Call<Void>

    @POST("upload/contacts")
    fun uploadContact(@Body networkContact: NetworkContact): Call<Void>

    @POST("upload/my_collection")
    fun uploadMyCollection(@Body networkMyCollection: NetworkMyCollection): Call<Void>

    @POST("upload/wishlist")
    fun uploadWishlist(@Body wishlist: NetworkWishlist): Call<Void>

    @POST("upload/want_to_play")
    fun uploadWantToPlay(@Body wantToPlay: NetworkWantToPlay): Call<Void>
}