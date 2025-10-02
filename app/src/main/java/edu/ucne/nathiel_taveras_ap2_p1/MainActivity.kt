package edu.ucne.nathiel_taveras_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.nathiel_taveras_ap2_p1.Presentation.Edit.EditEntradasScreen
import edu.ucne.nathiel_taveras_ap2_p1.Presentation.List.EntradasListScreen
import edu.ucne.nathiel_taveras_ap2_p1.ui.theme.Nathiel_Taveras_AP2_P1Theme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nathiel_Taveras_AP2_P1Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "entradasList"
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
                    composable("editEntradas/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        EditEntradasScreen(IdEntrada = id)
                    }
                }
            }
        }
    }
}