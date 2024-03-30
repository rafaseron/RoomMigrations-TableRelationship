package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GerenciaUsuariosUiState(
    val usuarios: List<String> = emptyList()
)

@HiltViewModel
class GerenciaUsuariosViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(GerenciaUsuariosUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaDados()
        }
    }

    private suspend fun carregaDados() {
    }
}