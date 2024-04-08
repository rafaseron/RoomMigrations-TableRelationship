package br.com.alura.helloapp.ui.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.localData.preferences.PreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SessaoUiState(val logado: Boolean = true)
@HiltViewModel
class SessaoViewModel @Inject constructor (private val dataStore: DataStore<Preferences>): ViewModel() {
    private val _uiState = MutableStateFlow(SessaoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.data.collect {
                if(it[PreferencesKey.AUTHENTICATED_USER] == null){
                    logout()
                    _uiState.value = _uiState.value.copy(logado = false)
                }
            }
        }

    }

    suspend fun logout(){
        dataStore.edit {
                preferences ->
            preferences[PreferencesKey.LOGADO] = false
        }
    }

}

