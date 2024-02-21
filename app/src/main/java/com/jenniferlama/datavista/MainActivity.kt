package com.jenniferlama.datavista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
            DataVistaApp()
        }
    }
}

@Composable
fun DataVistaApp() {
    DataVistaTheme {
        val navController = rememberNavController()
        val viewModel: DataVistaViewModel = viewModel()
        NavHost(navController = navController, startDestination = "notas") {
            composable(route = "notas") {
                NotasScreen(navController = navController, viewModel = viewModel)
            }
            composable(
                route = "contenido/{nota}",
                arguments = listOf(navArgument("nota") { type = NavType.StringType })
            ) { backStackEntry ->
                ContenidoScreen(
                    nota = backStackEntry.arguments?.getString("nota") ?: "",
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasScreen(navController: NavController, viewModel: DataVistaViewModel) {
    val notas = listOf(
        "Nota 1",
        "Nota 2",
        "Nota 3",
        "Nota 4"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(0xFF26A69A),
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            "DataVista",
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Image(
                            painter = painterResource(id = R.drawable.logodatavista),
                            contentDescription = "Imagen de un bloc de notas",
                            modifier = Modifier
                                .size(35.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
                .padding(top = 10.dp)
        ) {
            items(notas) { nota ->
                Row(
                    modifier = Modifier
                        .clickable { navController.navigate("contenido/$nota") }
                        .padding(vertical = 5.dp, horizontal = 12.dp)
                        .fillMaxWidth()
                        .background(Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = nota,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContenidoScreen(nota: String, viewModel: DataVistaViewModel) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Contenido de la nota:", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = nota, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataVistaTheme {
        val navController = rememberNavController()
        NotasScreen(navController = navController, viewModel = DataVistaViewModel())
    }
}
