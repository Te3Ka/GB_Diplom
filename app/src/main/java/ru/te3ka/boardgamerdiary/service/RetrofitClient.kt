package ru.te3ka.boardgamerdiary.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Объект для конфигурации Retrofit и создания экземпляра API-сервиса.
 * Используется для настройки и управления запросами к серверу.
 */
object RetrofitClient {
    private const val BASE_URL = "http://192.168.31.193:8080/"

    /**
     * Экземпляр Retrofit, настроенный для выполнения HTTP-запросов.
     *
     * Retrofit используется для создания экземпляров API-сервисов, которые могут отправлять запросы
     * к серверу и получать ответы.
     */
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Экземпляр ApiService, который предоставляет методы для взаимодействия с API.
     *
     * Создается с использованием ранее настроенного экземпляра Retrofit.
     */
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}