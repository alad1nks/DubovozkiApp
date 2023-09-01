package com.app.dubovozkiapp.ui.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dubovozkiapp.user.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {
    private val _darkTheme: MutableLiveData<Boolean> = MutableLiveData(userManager.isDarkTheme())
    val darkTheme: LiveData<Boolean> get() = _darkTheme

    private val _dynamicColor: MutableLiveData<Boolean> = MutableLiveData(userManager.isDynamicColor())
    val dynamicColor: LiveData<Boolean> get() = _dynamicColor

    fun changeDarkMode() {
        darkTheme.value?.let {
            userManager.setDarkTheme(!it)
            _darkTheme.value = !it
        }
    }

    fun changeDynamicColor() {
        dynamicColor.value?.let {
            userManager.setDynamicColor(!it)
            _dynamicColor.value = !it
        }
    }
}