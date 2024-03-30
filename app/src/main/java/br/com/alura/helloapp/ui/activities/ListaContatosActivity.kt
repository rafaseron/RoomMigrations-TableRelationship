package br.com.alura.helloapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.navigation.NavHostComposable
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaContatosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloAppTheme {
                val navController = rememberNavController()
                NavHostComposable(
                    navController = navController,
                )
            }
        }
    }
}