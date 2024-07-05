package ru.te3ka.boardgamerdiary.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkContact
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkMyCollection
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkProfile
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWantToPlay
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWishlist

/**
 * Интерфейс для определения API-запросов, связанных с загрузкой данных на сервер.
 * Использует Retrofit для выполнения HTTP-запросов к серверу.
 */
interface ApiService {

    /**
     * Отправляет профиль пользователя на сервер.
     *
     * @param networkProfile Объект, содержащий данные профиля пользователя.
     * @return [Call] для выполнения POST-запроса к эндпоинту "upload/profile".
     */
    @POST("upload/profile")
    fun uploadProfile(@Body networkProfile: NetworkProfile): Call<Void>

    /**
     * Отправляет контактные данные на сервер.
     *
     * @param networkContact Объект, содержащий данные контакта.
     * @return [Call] для выполнения POST-запроса к эндпоинту "upload/contacts".
     */
    @POST("upload/contacts")
    fun uploadContact(@Body networkContact: NetworkContact): Call<Void>

    /**
     * Отправляет данные о коллекции на сервер.
     *
     * @param networkMyCollection Объект, содержащий данные о коллекции.
     * @return [Call] для выполнения POST-запроса к эндпоинту "upload/my_collection".
     */
    @POST("upload/my_collection")
    fun uploadMyCollection(@Body networkMyCollection: NetworkMyCollection): Call<Void>

    /**
     * Отправляет данные о списке желаемых товаров на сервер.
     *
     * @param wishlist Объект, содержащий данные о списке желаемых товаров.
     * @return [Call] для выполнения POST-запроса к эндпоинту "upload/wishlist".
     */
    @POST("upload/wishlist")
    fun uploadWishlist(@Body wishlist: NetworkWishlist): Call<Void>

    /**
     * Отправляет данные о планируемых покупках на сервер.
     *
     * @param wantToPlay Объект, содержащий данные о планируемых покупках.
     * @return [Call] для выполнения POST-запроса к эндпоинту "upload/want_to_play".
     */
    @POST("upload/want_to_play")
    fun uploadWantToPlay(@Body wantToPlay: NetworkWantToPlay): Call<Void>
}