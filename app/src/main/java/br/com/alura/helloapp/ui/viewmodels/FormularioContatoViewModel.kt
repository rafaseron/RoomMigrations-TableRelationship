package br.com.alura.helloapp.ui.viewmodels

import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.R
import br.com.alura.helloapp.localData.room.entity.Contato
import br.com.alura.helloapp.localData.room.dao.ContatoDao
import br.com.alura.helloapp.extensions.converteParaDate
import br.com.alura.helloapp.extensions.converteParaString
import br.com.alura.helloapp.localData.room.repository.ContatoRepository
import br.com.alura.helloapp.util.ID_CONTATO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class FormularioContatoUiState(
    val id: Long = 0L,
    val nome: String = "",
    val sobrenome: String = "",
    val telefone: String = "",
    val fotoPerfil: String = "",
    val aniversario: Date? = null,
    val mostrarCaixaDialogoImagem: Boolean = false,
    val mostrarCaixaDialogoData: Boolean = false,
    val onNomeMudou: (String) -> Unit = {},
    val onSobrenomeMudou: (String) -> Unit = {},
    val onTelefoneMudou: (String) -> Unit = {},
    val onFotoPerfilMudou: (String) -> Unit = {},
    val onAniversarioMudou: (String) -> Unit = {},
    val onMostrarCaixaDialogoImagem: (mostrar: Boolean) -> Unit = {},
    val onMostrarCaixaDialogoData: (mostrar: Boolean) -> Unit = {},
    val textoAniversairo: String = "",
    @StringRes val tituloAppbar: Int? = R.string.titulo_cadastro_contato,
)

@HiltViewModel
class FormularioContatoViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val contatoRepository: ContatoRepository, private val dataStore: DataStore<Preferences>): ViewModel() {

    private val idContato = savedStateHandle.get<Long>(ID_CONTATO)

    private val _uiState = MutableStateFlow(FormularioContatoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaContato()
        }

        _uiState.update { state ->
            state.copy(onNomeMudou = {
                _uiState.value = _uiState.value.copy(
                    nome = it
                )
            }, onSobrenomeMudou = {
                _uiState.value = _uiState.value.copy(
                    sobrenome = it
                )
            }, onTelefoneMudou = {
                _uiState.value = _uiState.value.copy(
                    telefone = it
                )
            }, onFotoPerfilMudou = {
                _uiState.value = _uiState.value.copy(
                    fotoPerfil = it
                )
            }, onAniversarioMudou = {
                _uiState.value = _uiState.value.copy(
                    aniversario = it.converteParaDate(), mostrarCaixaDialogoData = false
                )
            }, onMostrarCaixaDialogoImagem = {
                _uiState.value = _uiState.value.copy(
                    mostrarCaixaDialogoImagem = it
                )
            }, onMostrarCaixaDialogoData = {
                _uiState.value = _uiState.value.copy(
                    mostrarCaixaDialogoData = it
                )
            })
        }
    }

    private suspend fun carregaContato() {
        idContato?.let {
            val contato = contatoRepository.searchContactFromId(idContato)
            contato.collect {
                it?.let {
                    with(it) {
                        _uiState.value = _uiState.value.copy(
                            id = id,
                            nome = nome,
                            sobrenome = sobrenome,
                            aniversario = aniversario,
                            telefone = telefone,
                            fotoPerfil = fotoPerfil,
                            tituloAppbar = R.string.titulo_editar_contato,
                        )
                    }
                }
            }
        }
    }

    fun defineTextoAniversario(textoAniversario: String) {
        val textoAniversairo = _uiState.value.aniversario?.converteParaString() ?: textoAniversario

        _uiState.update {
            it.copy(textoAniversairo = textoAniversairo)
        }
    }

    fun carregaImagem(url: String) {
        _uiState.value = _uiState.value.copy(
            fotoPerfil = url, mostrarCaixaDialogoImagem = false
        )
    }

    suspend fun salva() {
        with(_uiState.value) {
            contatoRepository.insertContato(
                Contato(
                    id = id,
                    nome = nome,
                    sobrenome = sobrenome,
                    telefone = telefone,
                    fotoPerfil = fotoPerfil,
                    aniversario = aniversario
                )
            )
        }
    }
}