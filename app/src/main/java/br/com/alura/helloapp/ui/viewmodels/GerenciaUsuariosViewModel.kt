package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.localData.room.entity.Usuario
import br.com.alura.helloapp.localData.room.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GerenciaUsuariosUiState(
    val listUsuarios: List<Usuario> = emptyList()
)

@HiltViewModel
class GerenciaUsuariosViewModel @Inject constructor(private val usuarioRepository: UsuarioRepository): ViewModel() {

    private val _uiState = MutableStateFlow(GerenciaUsuariosUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaDados()
        }
    }

    private suspend fun carregaDados() {
        val listaUsuarios = usuarioRepository.getAllUsers().first()
        _uiState.value = _uiState.value.copy(listUsuarios = listaUsuarios)
    }
}