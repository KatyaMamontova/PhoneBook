package ru.mamontova.phonebook.db

import android.provider.BaseColumns
import android.provider.BaseColumns.*

object DB : BaseColumns {
    const val TABLE_NAME = "contacts"
    const val COLUMN_NAME = "name"
    const val COLUMN_PHONE_NUMBER = "phone_number"
    const val COLUMN_IS_FAVORITE = "column_is_favorite"

    const val DATABASE_NAME = "phone_book.db"
    const val DATABASE_VERSION = 1
    
    const val CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_PHONE_NUMBER TEXT," +
                "$COLUMN_IS_FAVORITE INTEGER);"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}