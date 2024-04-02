package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.localData.room.entity.Usuario
import br.com.alura.helloapp.localData.room.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class FormularioLoginUiState(
    val nome: String = "",
    val usuario: String = "",
    val senha: String = "",
    val onNomeMudou: (String) -> Unit = {},
    val onUsuarioMudou: (String) -> Unit = {},
    val onSenhaMudou: (String) -> Unit = {},
    val onClickSalvar: () -> Unit = {}
)

@HiltViewModel
class FormularioLoginViewModel @Inject constructor(private val usuarioRepository: UsuarioRepository): ViewModel() {

    private val _uiState = MutableStateFlow(FormularioLoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onNomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        nome = it
                    )
                },
                onUsuarioMudou = {
                    _uiState.value = _uiState.value.copy(
                        usuario = it
                    )
                },
                onSenhaMudou = {
                    _uiState.value = _uiState.value.copy(
                        senha = it
                    )
                }
            )
        }
    }

    suspend fun salvaLogin() {
        val newUser = Usuario(name = uiState.value.nome, password = uiState.value.senha, username = uiState.value.usuario)
        usuarioRepository.insert(newUser)
    }
}