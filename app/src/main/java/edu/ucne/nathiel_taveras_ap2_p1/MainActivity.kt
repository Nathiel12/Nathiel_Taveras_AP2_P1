package edu.ucne.nathiel_taveras_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.nathiel_taveras_ap2_p1.Presentation.Edit.EditEntradasScreen
import edu.ucne.nathiel_taveras_ap2_p1.Presentation.List.EntradasListScreen
import edu.ucne.nathiel_taveras_ap2_p1.ui.theme.Nathiel_Taveras_AP2_P1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nathiel_Taveras_AP2_P1Theme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Registro de Huacales",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = "entradasList",
                        modifier = androidx.compose.ui.Modifier.padding(padding)
                    ) {
                        composable("entradasList") {
                            EntradasListScreen(
                                onNavigateToEdit = { id ->
                                    navController.navigate("editEntrada/$id")
                                },
                                onNavigateToCreate = {
                                    navController.navigate("editEntrada/0")
                                }
                            )
                        }
                        composable("editEntrada/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                            EditEntradasScreen(
                                IdEntrada = id,
                                onSaveComplete = {
                                    navController.popBackStack()
                                },
                                onDeleteComplete = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}