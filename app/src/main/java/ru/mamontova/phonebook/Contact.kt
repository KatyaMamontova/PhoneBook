package ru.mamontova.phonebook

import android.widget.Button

data class Contact(val imageResource: Int,
                   val text1: String,
                   val text2: String,
                   var isFaveImgResource: Int,
                   val deleteImgResource: Int,
                   var isFavorite: Any?)