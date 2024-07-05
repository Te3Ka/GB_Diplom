package ru.te3ka.boardgamerdiary

import androidx.lifecycle.Observer

/**
 * Наблюдатель для `Event`, который обрабатывает только те события, которые еще не были обработаны.
 *
 * Этот класс предназначен для использования в LiveData наблюдателях для обработки событий
 * только один раз, предотвращая повторное получение и обработку тех же событий.
 *
 * @param T Тип содержимого события.
 * @param onEventUnhandledContent Лямбда-функция, которая вызывается для обработки содержимого
 *                                события, если оно еще не было обработано.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit)
    : Observer<Event<T>> {

    /**
     * Вызывается при обновлении LiveData.
     * Обрабатывает событие, если оно еще не было обработано.
     *
     * @param event Событие, которое необходимо обработать.
     */
    override fun onChanged(event: Event<T>) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}