package com.jenniferlama.datavista

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataVistaViewModel @Inject constructor() : ViewModel(){
    private val _notaSeleccionada = MutableLiveData<String>()
    val notaSeleccionada: LiveData<String> = _notaSeleccionada

    fun seleccionarNota(nota: String) {
        _notaSeleccionada.value = nota
    }
}