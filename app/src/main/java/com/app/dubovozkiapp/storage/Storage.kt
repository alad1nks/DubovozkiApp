package com.app.dubovozkiapp.storage

interface Storage {
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
    fun setInt(key: String, value: Int)
    fun getInt(key: String): Int
    fun setString(key: String, value: String)
    fun getString(key: String): String
}