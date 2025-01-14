package com.githukudenis.comlib.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.comlib.feature.auth.presentation.login.LoginRoute
import com.githukudenis.comlib.feature.auth.presentation.reset.ResetPasswordRoute
import com.githukudenis.comlib.feature.auth.presentation.signup.SignUpRoute

fun NavGraphBuilder.authGraph(
    snackbarHostState: SnackbarHostState,
    onSignUpInstead: () -> Unit,
    onLoginComplete: () -> Unit,
    onSignUpComplete: () -> Unit,
    onResetComplete: () -> Unit,
    onForgotPassword: () -> Unit,
    onSignInInstead: () -> Unit
) {
    navigation(
        startDestination = AuthDestination.Login.route, route = ComlibDestination.AuthGraph.route
    ) {
        composable(route = AuthDestination.Login.route) {
            LoginRoute(
                onLoginComplete = onLoginComplete,
                onForgotPassword = onForgotPassword,
                onSignUpInstead = onSignUpInstead
            )
        }
        composable(route = AuthDestination.SignUp.route) {
            SignUpRoute(onSignUpComplete = onSignUpComplete, onSignInInstead = onSignInInstead)
        }
        composable(route = AuthDestination.ForgotPassword.route) {
            ResetPasswordRoute(snackbarHostState = snackbarHostState, onReset = onResetComplete)
        }
    }
}

sealed class AuthDestination(val route: String) {
    data object Login : AuthDestination("login")
    data object SignUp : AuthDestination("signup")
    data object ForgotPassword : AuthDestination("forgot_password")
}