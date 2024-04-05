package br.com.alura.helloapp.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import br.com.alura.helloapp.navigation.typeSafety.FormularioUsuario
import br.com.alura.helloapp.navigation.typeSafety.ListaUsuarios
import br.com.alura.helloapp.navigation.typeSafety.TypeSafetyNavigation
import br.com.alura.helloapp.ui.screens.CaixaDialogoContasUsuario
import br.com.alura.helloapp.ui.screens.FormularioUsuarioTela
import br.com.alura.helloapp.ui.screens.GerenciaUsuariosTela
import br.com.alura.helloapp.ui.viewmodels.FormularioUsuarioViewModel
import br.com.alura.helloapp.ui.viewmodels.GerenciaUsuariosViewModel
import br.com.alura.helloapp.ui.viewmodels.ListaUsuariosViewModel
import br.com.alura.helloapp.util.ID_USUARIO_ATUAL
import kotlinx.coroutines.launch

fun NavGraphBuilder.usuariosGraphNavigation(onVolta: () -> Unit, onNavegaParaLogin: () -> Unit,
                                            onNavegaParaHome: () -> Unit, onNavegaGerenciaUsuarios: () -> Unit, onNavegaParaFormularioUsuario: (String) -> Unit) {

    navigation(startDestination = ListaUsuarios.rota, route = TypeSafetyNavigation.UsuariosGraph.rota) {

        dialog(route = ListaUsuarios.rotaComArgumentos, arguments = ListaUsuarios.argumentos) {
            navBackStackEntry ->
            navBackStackEntry.arguments?.getString(ID_USUARIO_ATUAL)?.let {
                usuarioAtual ->
                Log.e("ATUAL", "no Navigation -> $usuarioAtual")

                val viewModel = hiltViewModel<ListaUsuariosViewModel>()
                val state by viewModel.uiState.collectAsState()
                val scope = rememberCoroutineScope()

                CaixaDialogoContasUsuario(state = state, onClickDispensa = onVolta,
                    onClickAdicionaNovaConta = { onNavegaParaLogin() },
                    onUsernameClick = {
                        username ->
                        scope.launch {
                            viewModel.trocarEntreSuasContas(username)
                            onNavegaParaHome()
                        }
                    },
                    onClickGerenciaUsuarios = { onNavegaGerenciaUsuarios() } )

            }
        }

        composable(route = TypeSafetyNavigation.GerenciaUsuarios.rota) {

            val viewModel = hiltViewModel<GerenciaUsuariosViewModel>()
            val state by viewModel.uiState.collectAsState()

            GerenciaUsuariosTela(state = state, onClickAbreDetalhes = {
                usuarioAtual ->
                    onNavegaParaFormularioUsuario(usuarioAtual)
                }, onClickVolta = onVolta )

        }

        composable(route = FormularioUsuario.rotaComArgumentos, arguments = FormularioUsuario.argumentos) {
            usuarioAtual ->
            val viewModel = hiltViewModel<FormularioUsuarioViewModel>()
            val state by viewModel.uiState.collectAsState()
            val coroutineScope = rememberCoroutineScope()

            FormularioUsuarioTela(state = state, onClickVolta = onVolta, onClickSalva = {
                    coroutineScope.launch {
                        onVolta()
                    }
                }, onClickApaga = {
                    coroutineScope.launch {
                        onVolta()
                    }
                }, onClickMostraMensagemExclusao = {
                    viewModel.onClickMostraMensagemExclusao()
                } )

        }

    }
}
