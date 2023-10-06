package com.app.dubovozkiapp.user

import com.app.dubovozkiapp.storage.Storage
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_LOGGED_IN = "user_logged_in"
private const val DARK_THEME = "dark_theme"
private const val DYNAMIC_COLOR = "dynamic_color"
private const val REVISION = "revision"


@Singleton
class UserManager @Inject constructor(
    private val storage: Storage
) {
    fun isUserLoggedIn() = storage.getBoolean(USER_LOGGED_IN)
    fun isDarkTheme() = storage.getBoolean(DARK_THEME)
    fun isDynamicColor() = storage.getBoolean(DYNAMIC_COLOR)
    fun getRevision() = storage.getInt(REVISION)


    fun userLogIn() { storage.setBoolean(USER_LOGGED_IN, true) }
    fun userLogOut() { storage.setBoolean(USER_LOGGED_IN, false) }
    fun setDarkTheme(darkTheme: Boolean) { storage.setBoolean(DARK_THEME, darkTheme) }
    fun setDynamicColor(dynamicColor: Boolean) { storage.setBoolean(DYNAMIC_COLOR, dynamicColor) }
    fun updateRevision() { storage.setInt(REVISION, getRevision() + 1) }
}