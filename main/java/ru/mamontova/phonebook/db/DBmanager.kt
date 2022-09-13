package ru.mamontova.phonebook.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DBmanager(val context: Context) {
    private val dbHelper = DBhelper(context)
    var db: SQLiteDatabase? = null

    fun openDB() {
        db = dbHelper.writableDatabase
    }

    fun insertToDB(name: String, phoneNumber: String, isFavorite: Int) {
        val values = ContentValues().apply {
            put(DB.COLUMN_NAME, name)
            put(DB.COLUMN_PHONE_NUMBER, phoneNumber)
            put(DB.COLUMN_IS_FAVORITE, phoneNumber)
        }
        db?.insert(DB.TABLE_NAME, null, values)
    }

    //TODO: изменить с имени на id
    fun removeFromDB(name: String) {
        db?.delete(DB.TABLE_NAME, DB.COLUMN_NAME + "=?", arrayOf(name))
    }

    fun switchFavs(name: String) {

        val cv = ContentValues()
        val status = isFavorite(name)
        println("STATUS $status")

        if (status==1) cv.put(DB.COLUMN_IS_FAVORITE, 0) else cv.put(DB.COLUMN_IS_FAVORITE, 1)
        db?.update(DB.TABLE_NAME, cv,DB.COLUMN_NAME + "=?", arrayOf(name))
    }

    fun isFavorite(name: String): Int? {
        val db = dbHelper.readableDatabase
        db?.rawQuery(
            "SELECT ${DB.COLUMN_IS_FAVORITE} " +
                    "FROM ${DB.TABLE_NAME} WHERE ${DB.COLUMN_NAME} = ?", arrayOf(name)
        ).use {
            if(it?.moveToFirst() == true) {
                return it?.getColumnIndex(DB.COLUMN_IS_FAVORITE)?.let { it1 -> it?.getInt(it1) }
            }
        }
        return null
    }

    @SuppressLint("Range")

    fun readDbData() : ArrayList<Map<String, Any>>{
        val dataList = ArrayList<Map<String, Any>>()
        openDB()
        val cursor = db?.query(DB.TABLE_NAME, null, null, null, null, null, null)

        try {
        while (cursor?.moveToNext()!!) {    //если все элементы закончатся, while остановится
            val name = cursor.getString(cursor.getColumnIndex(DB.COLUMN_NAME))
            val phone = cursor.getString(cursor.getColumnIndex(DB.COLUMN_PHONE_NUMBER))
            val isFavoriteStr = cursor.getString(cursor.getColumnIndex(DB.COLUMN_IS_FAVORITE))
            //чегоо он сам это предложил:
            val isFavorite = isFavoriteStr=="1"
            val contact = mapOf("name" to name.toString(), "phone" to phone.toString(), "isFavorite" to isFavorite)
            dataList.add(contact)
        }} catch (e: NullPointerException) {
            e.printStackTrace();
        } finally {
            cursor?.close()
        }

        return dataList
    }

    @SuppressLint("Range")
    fun selectFaves() : ArrayList<Map<String, Any>> {
        val dataList = ArrayList<Map<String, Any>>()
        openDB()
        val cursor = db?.rawQuery(
                "SELECT * " +
                        "FROM ${DB.TABLE_NAME} WHERE ${DB.COLUMN_IS_FAVORITE} = ?", arrayOf("1"))

        try {
            while (cursor?.moveToNext()!!) {    //если все элементы закончатся, while остановится
                val name = cursor.getString(cursor.getColumnIndex(DB.COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(DB.COLUMN_PHONE_NUMBER))
                val contact = mapOf("name" to name.toString(), "phone" to phone.toString())
                dataList.add(contact)
            }} catch (e: NullPointerException) {
            e.printStackTrace();
        } finally {
            cursor?.close()
        }

        return dataList
    }

    fun closeDB() {
        dbHelper.close()
    }

}