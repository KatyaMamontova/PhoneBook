package ru.mamontova.phonebook

data class Contact(val imageResource: Int,
                   val text1: String,
                   val text2: String,
                   var isFaveImgResource: Int,
                   val deleteImgResource: Int,
                   var isFavorite: Any?)