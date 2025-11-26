package edu.pe.tecsup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.pe.tecsup.data.model.Curso
import edu.pe.tecsup.ui.auth.LoginScreen
import edu.pe.tecsup.ui.auth.RegisterScreen
import edu.pe.tecsup.ui.home.*

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val ADD_CURSO = "add_curso"
    const val EDIT_CURSO = "edit_curso"
}

@Composable
fun AuthApp() {
    val navController = rememberNavController()
    AuthNavGraph(navController)
}

@Composable
fun AuthNavGraph(navController: NavHostController) {
    var cursoToEdit: Curso? = null
    
    NavHost(navController = navController,
        startDestination = Destinations.LOGIN)
    {
        composable(Destinations.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.REGISTER){
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Destinations.HOME){
            val viewModel: CursoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            HomeScreen(
                onLogout = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.HOME) { inclusive = true }
                    }
                },
                onNavigateToAdd = {
                    navController.navigate(Destinations.ADD_CURSO)
                },
                onNavigateToEdit = { curso ->
                    cursoToEdit = curso
                    navController.navigate(Destinations.EDIT_CURSO)
                },
                viewModel = viewModel
            )
        }
        
        composable(Destinations.ADD_CURSO) {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry(Destinations.HOME)
            }
            val viewModel: CursoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(parentEntry)
            AddCursoScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
        
        composable(Destinations.EDIT_CURSO) {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry(Destinations.HOME)
            }
            val viewModel: CursoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(parentEntry)
            cursoToEdit?.let { curso ->
                EditCursoScreen(
                    curso = curso,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}