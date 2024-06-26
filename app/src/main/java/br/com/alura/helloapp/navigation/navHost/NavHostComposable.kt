package br.com.alura.helloapp.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alura.helloapp.navigation.buscaContatosScreenNavigation
import br.com.alura.helloapp.navigation.detalhesContatoScreenNavigation
import br.com.alura.helloapp.navigation.formularioContatoScreenNavigation
import br.com.alura.helloapp.navigation.listaContatosScreenNavigation
import br.com.alura.helloapp.navigation.loginGraphNavigation
import br.com.alura.helloapp.navigation.splashNavigation
import br.com.alura.helloapp.navigation.typeSafety.DetalhesContato
import br.com.alura.helloapp.navigation.typeSafety.FormularioContato
import br.com.alura.helloapp.navigation.typeSafety.FormularioUsuario
import br.com.alura.helloapp.navigation.typeSafety.ListaUsuarios
import br.com.alura.helloapp.navigation.typeSafety.TypeSafetyNavigation
import br.com.alura.helloapp.navigation.usuariosGraphNavigation
import br.com.alura.helloapp.ui.viewmodels.SessaoViewModel

@Composable
fun NavHostComposable(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = TypeSafetyNavigation.SplashScreen.rota, modifier = modifier) {

        splashNavigation(onNavegaParaLogin = { navController.navigateToLoginScreenAndClearBacktack() }, onNavegaParaHome = { navController.navegaParaHome() })

        loginGraphNavigation(onNavegaParaHome = { navController.navegaParaHome() }, onNavegaParaFormularioLogin = { navController.navegaParaFormlarioLogin() },
            onNavegaParaLogin = { navController.navigateToLoginScreenAndClearBacktack() })

        listaContatosScreenNavigation(onNavegaParaDetalhes = { idContato -> navController.navegaParaDetalhes(idContato) },
            onNavegaParaFormularioContato = { navController.navegaParaFormularioContato() },
            onNavegaParaDialgoUsuarios = { idUsuario -> navController.navegaParaDialgoUsuarios(idUsuario) },
            onNavegaParaBuscaContatos = { navController.navegaParaBuscaContatos() })

        formularioContatoScreenNavigation(onVolta = { navController.popBackStack() })

        detalhesContatoScreenNavigation(onVolta = { navController.popBackStack() }, onNavegaParaDialgoUsuarios = { idContato -> navController.navegaParaFormularioContato(idContato) })

        usuariosGraphNavigation(onVolta = { navController.popBackStack() }, onNavegaParaLogin = { navController.navegaParaLogin() },
            onNavegaParaHome = { navController.navegaParaHome() }, onNavegaGerenciaUsuarios = { navController.navegaParaGerenciaUsuarios() },
            onNavegaParaFormularioUsuario = { idUsuario -> navController.navegaParaFormularioUsuario(idUsuario) })

        buscaContatosScreenNavigation(onVolta = { navController.popBackStack() }, onClickNavegaParaDetalhesContato = { idContato -> navController.navegaParaDetalhes(idContato) })

    }

    //NavHost acompanhando atualizacao no Estado de login para direcionar o Usuario para tela de Login caso deslogue da conta atual
    val viewModel: SessaoViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()
    LaunchedEffect(uiState.value.logado) {
        if(!uiState.value.logado){
            navController.navigateToLoginScreenAndClearBacktack()
        }
    }
}


fun NavHostController.navegaDireto(rota: String) = this.navigate(rota) {
    popUpTo(this@navegaDireto.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavHostController.navegaLimpo(rota: String) = this.navigate(rota) {
    popUpTo(0)
}

fun NavHostController.navegaParaDetalhes(idContato: Long) {
    navegaDireto("${DetalhesContato.rota}/$idContato")
}

fun NavHostController.navegaParaFormularioContato(idContato: Long = 0L) {
    navigate("${FormularioContato.rota}/$idContato")
}

fun NavHostController.navigateToLoginScreenAndClearBacktack() {
    navegaLimpo(TypeSafetyNavigation.LoginGraph.rota)
}

fun NavHostController.navegaParaDialgoUsuarios(idUsuario: String) {
    navigate("${ListaUsuarios.rota}/$idUsuario")
}

fun NavHostController.navegaParaFormularioUsuario(idUsuario: String) {
    navigate("${FormularioUsuario.rota}/$idUsuario")
}

fun NavHostController.navegaParaLogin() {
    navigate(TypeSafetyNavigation.Login.rota)
}

fun NavHostController.navegaParaHome() {
    navegaLimpo(TypeSafetyNavigation.HomeGraph.rota)
}

fun NavHostController.navegaParaFormlarioLogin() {
    navigate(TypeSafetyNavigation.FormularioLogin.rota)
}

fun NavHostController.navegaParaGerenciaUsuarios() {
    navigate(TypeSafetyNavigation.GerenciaUsuarios.rota)
}

fun NavHostController.navegaParaBuscaContatos() {
    navigate(TypeSafetyNavigation.BuscaContatos.rota)
}
