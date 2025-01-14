package com.githukudenis.comlib.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.feature.books.BooksRoute
import com.githukudenis.comlib.feature.home.HomeRoute


fun NavGraphBuilder.homeNavGraph(
    appState: AppState,
    startDestination: String = HomeDestination.Home.route,
) {
    navigation(startDestination = startDestination, route = ComlibDestination.HomeGraph.route) {
        composable(enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }, popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        }, route = HomeDestination.Home.route) {
            HomeRoute(onOpenBookDetails = { bookId ->
                appState.navigate(
                    route = "${ComlibDestination.BookDetail.route}/$bookId",
                    popUpTo = "${ComlibDestination.BookDetail.route}/$bookId"
                )
            }, onOpenAllBooks = {
                appState.navigate(
                    route = HomeDestination.Books.route, popUpTo = HomeDestination.Books.route
                )
            }, onOpenProfile = {
                appState.navigate(
                    route = ComlibDestination.Profile.route,
                    popUpTo = ComlibDestination.Profile.route
                )
            })
        }
        composable(route = HomeDestination.Clubs.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Clubs"
                )
            }
        }
        composable(
            route = HomeDestination.Books.route,
        ) {
            BooksRoute(onOpenBook = { bookId ->
                appState.navigate(
                    route = "${ComlibDestination.BookDetail.route}/$bookId",
                    popUpTo = "${ComlibDestination.BookDetail.route}/$bookId"
                )
            }, onNavigateUp = {
                appState.popBackStack()
            })
        }
    }
}


sealed class HomeDestination(
    val route: String,
    val label: String? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
) {
    data object Home : HomeDestination(
        route = "home",
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )

    data object Books : HomeDestination(
        route = "books",
        label = "Books",
        selectedIcon = Icons.Filled.LibraryBooks,
        unselectedIcon = Icons.Outlined.LibraryBooks,
    )

    data object Clubs : HomeDestination(
        route = "clubs",
        label = "Clubs",
        selectedIcon = Icons.Filled.People,
        unselectedIcon = Icons.Outlined.People,
    )

}