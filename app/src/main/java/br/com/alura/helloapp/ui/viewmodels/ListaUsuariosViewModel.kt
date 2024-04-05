package br.com.alura.helloapp.ui.viewmodels

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.localData.room.repository.UsuarioRepository
import br.com.alura.helloapp.util.ID_USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListaUsuariosUiState(
    val nomeDeUsuario: String? = null,
    val nome: String? = null,
)

@HiltViewModel
class ListaUsuariosViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val dataStore: DataStore<Preferences>, private val usuarioRepository: UsuarioRepository): ViewModel() {

    private val usuarioAtual = savedStateHandle.get<String>(ID_USUARIO_ATUAL)

    private val _uiState = MutableStateFlow(ListaUsuariosUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaDados()
        }
    }

    private suspend fun carregaDados() {
        usuarioAtual?.let {
            string ->
            Log.e("ATUAL", "no ViewModel -> $string")
            val usuarioPesquisado = usuarioRepository.searchUsername(string)
            usuarioPesquisado?.let {
                usuario ->
                _uiState.value = _uiState.value.copy(nomeDeUsuario = usuario.username, nome = usuario.name)
            }
        }
    }
}