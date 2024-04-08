package br.com.alura.helloapp.ui.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.localData.preferences.PreferencesKey
import br.com.alura.helloapp.localData.room.entity.Usuario
import br.com.alura.helloapp.localData.room.repository.ContatoRepository
import br.com.alura.helloapp.localData.room.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormularioUsuarioUiState(
    val nomeUsuario: String = "",
    val nome: String = "",
    val senha: String = "",
    val onNomeMudou: (String) -> Unit = {},
    val mostraMensagemExclusao: Boolean = false,
    val mostraMensagemExclusaoMudou: (Boolean) -> Unit = {},
    val usernameDoNavBackStackEntry: String? = null,
)

@HiltViewModel
class FormularioUsuarioViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val dataStore: DataStore<Preferences>,
                                                     private val usuarioRepository: UsuarioRepository, private val contatoRepository: ContatoRepository): ViewModel() {

    private val nomeUsuario = savedStateHandle.get<String>("idUsuario")

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

    fun receberUsernamePeloNavigation(username: String?){
        _uiState.value = _uiState.value.copy(usernameDoNavBackStackEntry = username)

        username?.let { atualizarUsuarioDoUiState(it) }

    }

    fun atualizarUsuarioDoUiState(username: String){
        viewModelScope.launch {
            val pesquisarUsuario = usuarioRepository.searchUsername(username)
            pesquisarUsuario?.let {
                _uiState.value = _uiState.value.copy(nome = it.name, nomeUsuario = it.username, senha = it.password)
            }
        }
    }

    suspend fun atualizarUsuarioOnSaveClick(){
        usuarioRepository.update(Usuario(name = uiState.value.nome, password = uiState.value.senha, username = uiState.value.nomeUsuario))
    }

    suspend fun apagarUsuarioOnDeleteClick(){
        usuarioRepository.delete(Usuario(name = uiState.value.nome, password = uiState.value.senha, username = uiState.value.nomeUsuario))
        contatoRepository.deleteAllContactsFromUsername(uiState.value.nomeUsuario)
        val preferences = dataStore.data.first()
        if (uiState.value.nomeUsuario == preferences[PreferencesKey.AUTHENTICATED_USER]){
            dataStore.edit {
                preferences ->
                preferences.remove(PreferencesKey.AUTHENTICATED_USER)
                //preferences[PreferencesKey.LOGADO] = false
            }
        }

    }

}