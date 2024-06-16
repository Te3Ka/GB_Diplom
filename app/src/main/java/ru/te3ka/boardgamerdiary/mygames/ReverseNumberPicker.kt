package ru.te3ka.boardgamerdiary.mygames

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.NumberPicker
import java.util.Calendar

class ReverseNumberPicker(context: Context, attrs: AttributeSet) : NumberPicker(context, attrs) {

    init {
        initialize()
    }

    private fun initialize() {
        minValue = 1900
        maxValue = Calendar.getInstance().get(Calendar.YEAR)
        wrapSelectorWheel = false
        displayedValues = (minValue..maxValue).reversed().map { it.toString() }.toTypedArray()

    }
}