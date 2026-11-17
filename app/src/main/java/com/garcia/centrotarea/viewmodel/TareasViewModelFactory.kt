package com.garcia.centrotarea.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.garcia.centrotarea.data.TareasRepository

class TareasViewModelFactory(private val repo: TareasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == TareasViewModel::class.java)
        @Suppress("UNCHECKED_CAST")
        return TareasViewModel(repo) as T
    }
}
