package br.com.alura.helloapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.alura.helloapp.navigation.typeSafety.TypeSafetyNavigation
import br.com.alura.helloapp.ui.screens.ListaContatosTela
import br.com.alura.helloapp.ui.viewmodels.ListaContatosViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.listaContatosScreenNavigation(onNavegaParaDetalhes: (Long) -> Unit, onNavegaParaFormularioContato: () -> Unit,
                                                  onNavegaParaDialgoUsuarios: (String) -> Unit, onNavegaParaBuscaContatos: () -> Unit) {

    navigation(startDestination = TypeSafetyNavigation.ListaContatos.rota, route = TypeSafetyNavigation.HomeGraph.rota) {

        composable(route = TypeSafetyNavigation.ListaContatos.rota) {
            val viewModel = hiltViewModel<ListaContatosViewModel>()
            val state by viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.buscaContatos()
                }
            }

            ListaContatosTela(state = state, onClickAbreDetalhes = { idContato -> onNavegaParaDetalhes(idContato) },
                onClickAbreCadastro = { onNavegaParaFormularioContato() },
                onClickListaUsuarios = {
                    // state.usuarioAtual?.let { usuarioAtual ->
                    onNavegaParaDialgoUsuarios("usuarioAtual")
                    // }
                }, onClickBuscaContatos = onNavegaParaBuscaContatos )

        }

    }
}
