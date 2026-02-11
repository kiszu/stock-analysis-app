package com.stockanalysis.app.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stockanalysis.app.presentation.ui.screens.DashboardScreen
import com.stockanalysis.app.presentation.ui.screens.SignalDetailScreen
import com.stockanalysis.app.presentation.ui.screens.SearchScreen

/**
 * Main App Navigation Graph
 */
@Composable
fun StockAnalysisApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        // Dashboard Screen
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onSignalClick = { symbol ->
                    navController.navigate(Screen.SignalDetail.createRoute(symbol))
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }

        // Signal Detail Screen
        composable(
            route = Screen.SignalDetail.route,
            arguments = listOf(
                navArgument("symbol") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
            SignalDetailScreen(
                symbol = symbol,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Search Screen
        composable(Screen.Search.route) {
            SearchScreen(
                onSymbolClick = { symbol ->
                    navController.navigate(Screen.SignalDetail.createRoute(symbol))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

/**
 * Navigation Destinations
 */
sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object SignalDetail : Screen("signal/{symbol}") {
        fun createRoute(symbol: String) = "signal/$symbol"
    }
    object Search : Screen("search")
    object Settings : Screen("settings")
    object Subscription : Screen("subscription")
}
