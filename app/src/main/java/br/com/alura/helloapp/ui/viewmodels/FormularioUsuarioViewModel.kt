package br.com.alura.helloapp.ui.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.util.ID_USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class FormularioUsuarioUiState(
    val nomeUsuario: String = "",
    val nome: String = "",
    val senha: String = "",
    val onNomeMudou: (String) -> Unit = {},
    val mostraMensagemExclusao: Boolean = false,
    val mostraMensagemExclusaoMudou: (Boolean) -> Unit = {},
)

@HiltViewModel
class FormularioUsuarioViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val dataStore: DataStore<Preferences>): ViewModel() {

    private val nomeUsuario = savedStateHandle.get<String>(ID_USUARIO_ATUAL)

    private val _uiState = MutableStateFlow(FormularioUsuarioUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(onNomeMudou = {
                _uiState.value = _uiState.value.copy(
                    nome = it
                )
            })
        }
    }

    fun onClickMostraMensagemExclusao() {
        _uiState.value = _uiState.value.copy(
            mostraMensagemExclusao = true
        )
    }
}