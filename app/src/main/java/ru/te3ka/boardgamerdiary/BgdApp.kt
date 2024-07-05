package ru.te3ka.boardgamerdiary

import android.app.Application
import ru.te3ka.boardgamerdiary.db.BgdDatabase

/**
 * Основной класс приложения.
 *
 * Этот класс расширяет [Application] и используется для инициализации базы данных
 * при запуске приложения. Он запускается до создания первой активности и обеспечивает
 * глобальный доступ к базе данных на протяжении всего жизненного цикла приложения.
 */
class BgdApp : Application() {
    /**
     * Этот метод вызывается при создании приложения.
     * Здесь происходит инициализация базы данных.
     */
    override fun onCreate() {
        super.onCreate()
        BgdDatabase.initializeDatabase(this)
    }
}