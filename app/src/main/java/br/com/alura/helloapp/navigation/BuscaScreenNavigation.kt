package br.com.alura.helloapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alura.helloapp.navigation.typeSafety.TypeSafetyNavigation
import br.com.alura.helloapp.ui.screens.BuscaContatosTela
import br.com.alura.helloapp.ui.viewmodels.BuscaContatosViewModel

fun NavGraphBuilder.buscaContatosScreenNavigation(onVolta: () -> Unit, onClickNavegaParaDetalhesContato: (Long) -> Unit) {

    composable(route = TypeSafetyNavigation.BuscaContatos.rota) {
        val viewModel = hiltViewModel<BuscaContatosViewModel>()
        val state by viewModel.uiState.collectAsState()

        BuscaContatosTela(state = state, onClickVolta = onVolta, onClickAbreDetalhes = { idContato -> onClickNavegaParaDetalhesContato(idContato) })

    }
}