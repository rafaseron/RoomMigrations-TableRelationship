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
import br.com.alura.helloapp.ui.screens.FormularioLoginTela
import br.com.alura.helloapp.ui.viewmodels.FormularioLoginViewModel
import br.com.alura.helloapp.ui.screens.LoginTela
import br.com.alura.helloapp.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.loginGraphNavigation(onNavegaParaHome: () -> Unit, onNavegaParaFormularioLogin: () -> Unit,
                                         onNavegaParaLogin: () -> Unit) {

    navigation(startDestination = TypeSafetyNavigation.Login.rota, route = TypeSafetyNavigation.LoginGraph.rota) {

        composable(route = TypeSafetyNavigation.Login.rota) {

            val viewModel = hiltViewModel<LoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            if (state.logado) {
                LaunchedEffect(Unit) {
                    onNavegaParaHome()
                }
            }

            val coroutineScope = rememberCoroutineScope()

            LoginTela(state = state, onClickLoga = {
                    coroutineScope.launch {
                        viewModel.tentaLogar()
                    }
                }, onClickCriaLogin = onNavegaParaFormularioLogin )

        }

        composable(route = TypeSafetyNavigation.FormularioLogin.rota) {

            val viewModel = hiltViewModel<FormularioLoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            val coroutineScope = rememberCoroutineScope()

            FormularioLoginTela(state = state, viewModel = viewModel, onSalva = {
                coroutineScope.launch {
                    viewModel.salvaLogin()
                }
                onNavegaParaLogin()
            })

        }

    }
}