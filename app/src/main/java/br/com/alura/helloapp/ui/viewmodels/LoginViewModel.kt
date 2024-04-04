package br.com.alura.helloapp.ui.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.localData.preferences.PreferencesKey
import br.com.alura.helloapp.localData.room.converter.HashConverter.Companion.toHash256
import br.com.alura.helloapp.localData.room.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val usuario: String = "",
    val senha: String = "",
    val exibirErro: Boolean = false,
    val onUsuarioMudou: (String) -> Unit = {},
    val onSenhaMudou: (String) -> Unit = {},
    val onClickLogar: () -> Unit = {},
    val logado: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(private val usuarioRepository: UsuarioRepository, private val dataStore: DataStore<Preferences>): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onUsuarioMudou = {
                    _uiState.value = _uiState.value.copy(
                        usuario = it
                    )
                },
                onSenhaMudou = {
                    _uiState.value = _uiState.value.copy(
                        senha = it
                    )
                },
            )
        }
    }

    suspend fun tentaLogar() {
        val tentarLogar = usuarioRepository.autenticarUsuario(username = uiState.value.usuario, password = uiState.value.senha)
        if (tentarLogar){
            logaUsuario()
        }else{
            exibeErro()
        }
    }

    private fun exibeErro() {
        _uiState.value = _uiState.value.copy(
            exibirErro = true
        )
    }

    private fun logaUsuario() {
        _uiState.value = _uiState.value.copy(logado = true)
        viewModelScope.launch {
            dataStore.edit {
                it[PreferencesKey.LOGADO] = true
            }
        }
    }
}

