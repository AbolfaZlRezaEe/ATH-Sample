package com.abp.noties.base

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class ATHViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    fun notNullStringFromSharedPrefs(
        sharedPreferences: SharedPreferences,
        key: String,
        defaultValue: String? = null
    ): String {
        val response = sharedPreferences.getString(key, defaultValue)
        return response ?: ""
    }
}