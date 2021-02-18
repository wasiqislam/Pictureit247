package com.pictureit24.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainProviderFactory(private val repository: MainRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }

}
