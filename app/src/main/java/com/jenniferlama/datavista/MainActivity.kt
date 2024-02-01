package com.jenniferlama.datavista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.jenniferlama.datavista.ui.theme.DataVistaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataVistaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "notas") {
                    composable(route = "notas") {
                        NotasScreen(navController = navController)
                    }
                    composable(
                        route = "contenido/{nota}",
                        arguments = listOf(navArgument("nota") { type = NavType.StringType })
                    ) { backStackEntry ->
                        ContenidoScreen(nota = backStackEntry.arguments?.getString("nota") ?: "")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("DataVista")
                        Image(
                            painter = painterResource(id = R.drawable.bloc_notas),
                            contentDescription = "Imagen de un bloc de notas",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LazyColumn {
                items(8) { objetoIndex ->
                    val contenido = when (objetoIndex) {
                        0 -> "Esta es la primera nota"
                        1 -> "Aqui se muestra el contenido de la segunda nota"
                        else -> "Contenido genérico"
                    }

                    Text(
                        text = "Nota $objetoIndex",
                        modifier = Modifier.clickable {
                            navController.navigate("contenido/$contenido")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ContenidoScreen(nota: String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Contenido de la nota:")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = nota)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataVistaTheme {
        val navController = rememberNavController()
        NotasScreen(navController = navController)
    }
}
