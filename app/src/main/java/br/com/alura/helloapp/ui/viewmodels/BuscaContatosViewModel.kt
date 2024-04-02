package br.com.alura.helloapp.ui.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.localData.room.dao.ContatoDao
import br.com.alura.helloapp.localData.room.entity.Contato
import br.com.alura.helloapp.localData.room.repository.ContatoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class BuscaContatosUiState(
    val contatos: List<Contato> = emptyList(),
    val usuarioAtual: String? = null,
    val valorBusca: String = "",
    val onValorBuscaMudou: (String) -> Unit = {}
)

@HiltViewModel
class BuscaContatosViewModel @Inject constructor(private val contatoRepository: ContatoRepository, private val dataStore: DataStore<Preferences>) : ViewModel() {

    private val _uiState = MutableStateFlow(BuscaContatosUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(onValorBuscaMudou = {
                _uiState.value = _uiState.value.copy(
                    valorBusca = it
                )
                buscaContatosPorValor()
            })
        }

    }

    fun buscaContatosPorValor() {

    }
}