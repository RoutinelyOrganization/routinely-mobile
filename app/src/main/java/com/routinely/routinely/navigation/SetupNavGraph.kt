package com.routinely.routinely.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.routinely.routinely.R
import com.routinely.routinely.changepassword.CreateNewPasswordScreen
import com.routinely.routinely.changepassword.CreateNewPasswordViewModel
import com.routinely.routinely.changepassword.ForgotPasswordScreen
import com.routinely.routinely.changepassword.ForgotPasswordViewModel
import com.routinely.routinely.changepassword.VerificationCodeScreen
import com.routinely.routinely.changepassword.VerificationCodeViewModel
import com.routinely.routinely.home.HomeScreen
import com.routinely.routinely.login.CreateAccountScreen
import com.routinely.routinely.login.CreateAccountViewModel
import com.routinely.routinely.login.LoginScreen
import com.routinely.routinely.login.LoginViewModel
import com.routinely.routinely.splash_screen.SplashScreen
import com.routinely.routinely.task.AddTaskScreen
import com.routinely.routinely.task.EditTaskScreen
import com.routinely.routinely.util.MenuItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDest: Screen,
) {
    NavHost(
        navController = navController,
        startDestination = startDest.route
    ) {
        loginRoute(
            navigateToHomeScreen = {
                navController.popBackStack(route = Screen.SplashScreen.route, inclusive = true)
                navController.navigate(Screen.HomeScreen.route)
            },
            navigateToCreateAccountScreen = {
                navController.navigate(Screen.CreateAccount.route)
            },
            navigateToForgotPasswordScreen = {
                navController.navigate(Screen.ForgotPasswordScreen.route)
            },
        )
        homeScreenRoute(
            onNotificationClicked = {
//                navController.navigate()
            },
            onNewTaskClicked = {
                navController.navigate(Screen.AddTaskScreen.route)
            },
            onEditTaskClicked = {
                navController.navigate(Screen.EditTaskScreen.route)
            },
        )
        addTaskScreenRoute(
            onBackButtonPressed = {
                navController.popBackStack()
            },
            onHomeButtonPressed = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            }
        )
        splashScreenRoute(
            onEmailLoginClicked = {
                navController.navigate(Screen.Login.route)
            }
        )
        createAccountRoute(
            onAlreadyHaveAnAccountClicked = {
                navController.navigate(Screen.Login.route)
            },
            navigateToLoginScreen = {
                navController.navigate(Screen.Login.route)
            }
        )
        forgotPasswordRoute(
            navigateToCodeVerificationScreen = {
                navController.navigate(Screen.VerificationCodeScreen.route)
            }
        )
        verificationCodeRoute(
            navigateToSetNewPasswordScreen = {
                navController.navigate(Screen.NewPasswordScreen.route)
            }
        )
        newPasswordRoute(
            navigateToLoginScreen = {
                navController.navigate(Screen.Login.route)
            }
        )

        editTaskScreenRoute(
            onBackButtonPressed = {
                navController.popBackStack()
            },
            onHomeButtonPressed = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            },
            onNotificationClicked = {}
        )
    }
}

fun NavGraphBuilder.loginRoute(
    navigateToHomeScreen: () -> Unit,
    navigateToCreateAccountScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
) {
    composable(route = Screen.Login.route) {
        val viewModel: LoginViewModel = koinViewModel()
        val authenticated by viewModel.authenticated.collectAsState()
        val signInResult by viewModel.signInResult.collectAsState()
        LoginScreen(
            authenticated = authenticated,
            loginWithEmailAndPassword = {
                viewModel.loginWithEmailAndPassword(it)
            },
            navigateToHomeScreen = navigateToHomeScreen,
            navigateToCreateAccountScreen = navigateToCreateAccountScreen,
            navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
            emailStateValidation = {
                viewModel.emailState(it)
            },
            passwordStateValidation = {
                viewModel.passwordState(it)
            },
            signInResult = signInResult,
            saveUser =  { token, remember ->
                viewModel.saveUser(token, remember)
            }
        )
    }
}
fun NavGraphBuilder.createAccountRoute(
    navigateToLoginScreen: () -> Unit,
    onAlreadyHaveAnAccountClicked: () -> Unit,
) {
    composable(route = Screen.CreateAccount.route) {
        val viewModel: CreateAccountViewModel = koinViewModel()
        val shouldGoToNextScreen by viewModel.shouldGoToNextScreen
        val intentForPrivacyPolicy = Intent(Intent.ACTION_VIEW)
        val apiErrorMessage by viewModel.apiErrorMessage.collectAsState()
        intentForPrivacyPolicy.setData(Uri.parse("swap.link.do.pdf"))
        CreateAccountScreen(
            onCreateAccountClicked = { userRegister ->
                viewModel.verifyAllConditions(userRegister)
            },
            onAlreadyHaveAnAccountClicked = onAlreadyHaveAnAccountClicked ,
            shouldGoToNextScreen = shouldGoToNextScreen,
            navigateToLoginScreen = navigateToLoginScreen,
            nameStateValidation = {
                viewModel.nameState(it)
            },
            emailStateValidation = {
                viewModel.emailState(it)
            },
            confirmPasswordStateValidation = { password, confirmPassword ->
                viewModel.confirmPasswordState(password, confirmPassword)
            },
            passwordStateValidation = {
                viewModel.passwordState(it)
            },
            intentForPrivacy = intentForPrivacyPolicy,
            apiErrorMessage = apiErrorMessage,
        )
    }
}
fun NavGraphBuilder.newPasswordRoute(
    navigateToLoginScreen: () -> Unit
) {
    composable(route = Screen.NewPasswordScreen.route) {
        val viewModel: CreateNewPasswordViewModel = koinViewModel()
        val apiErrorMessage by viewModel.apiErrorMessage.collectAsState()
        val shouldGoToNextScreen by viewModel.shouldGoToNextScreen
        CreateNewPasswordScreen(
            onUpdatePasswordClicked = { password :String, confirmPassword :String ->
                viewModel.verifyAllConditions(password, confirmPassword)
            },
            passwordStateValidation = {
                viewModel.passwordState(it)
            },
            apiErrorMessage = apiErrorMessage,
            shouldGoToNextScreen = shouldGoToNextScreen,
            navigateToLoginScreen = navigateToLoginScreen,
            confirmPasswordStateValidation = { password, confirmPassword ->
                viewModel.confirmPasswordState(password, confirmPassword)
            }
        )
    }
}
fun NavGraphBuilder.forgotPasswordRoute(
    navigateToCodeVerificationScreen: () -> Unit,
) {
    composable(route = Screen.ForgotPasswordScreen.route) {
        val viewModel: ForgotPasswordViewModel = koinViewModel()
        val apiErrorMessage by viewModel.apiErrorMessage.collectAsState()
        val shouldGoToNextScreen by viewModel.shouldGoToNextScreen
        ForgotPasswordScreen(
            navigateToCodeVerificationScreen = navigateToCodeVerificationScreen,
            onResetPasswordClicked = { email :String ->
                viewModel.verifyAllConditions(email)
            },
            emailStateValidation = {
                viewModel.emailState(it)
            },
            shouldGoToNextScreen = shouldGoToNextScreen,
            apiErrorMessage = apiErrorMessage
        )
    }
}
fun NavGraphBuilder.verificationCodeRoute(
    navigateToSetNewPasswordScreen: () -> Unit
) {
    composable(route = Screen.VerificationCodeScreen.route) {
        val viewModel: VerificationCodeViewModel = koinViewModel()
        val shouldGoToNextScreen by viewModel.shouldGoToNextScreen
        VerificationCodeScreen(
            onConfirmResetPasswordClicked = { code :String ->
                viewModel.verifyAllConditions(code)
            },
            codeStateValidation = { code :String ->
                viewModel.codeState(code)
            },
            navigateToSetNewPasswordScreen = navigateToSetNewPasswordScreen,
            shouldGoToNextScreen = shouldGoToNextScreen
        )
    }
}

fun NavGraphBuilder.homeScreenRoute(
    onNotificationClicked: () -> Unit,
    onNewTaskClicked: () -> Unit,
    onEditTaskClicked: () -> Unit,
) {
    composable(route = Screen.HomeScreen.route) {
        val menuItems = listOf(
            MenuItem(
                text = stringResource(R.string.menu_configuration),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_goal),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_notification),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_logout),
                onItemClick = { }
            ),
        )

        HomeScreen(
            onNotificationClicked = { onNotificationClicked() },
            onNewTaskClicked = { onNewTaskClicked() },
            onEditTaskClicked = { onEditTaskClicked() },
            menuItems = menuItems
        )
    }
}

fun NavGraphBuilder.addTaskScreenRoute(
    onBackButtonPressed: () -> Unit,
    onHomeButtonPressed: () -> Unit
) {
    composable(route = Screen.AddTaskScreen.route) {

        val menuItems = listOf(
            MenuItem(
                text = stringResource(R.string.menu_configuration),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_goal),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_notification),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_logout),
                onItemClick = { }
            ),
        )

        AddTaskScreen(
            onBackButtonPressed = onBackButtonPressed,
            onHomeButtonPressed = onHomeButtonPressed,
            menuItems = menuItems,
        )
    }
}

fun NavGraphBuilder.editTaskScreenRoute(
    onBackButtonPressed: () -> Unit,
    onNotificationClicked: () -> Unit,
    onHomeButtonPressed: () -> Unit,
) {
    composable(route = Screen.EditTaskScreen.route) {
        val menuItems = listOf(
            MenuItem(
                text = stringResource(R.string.menu_configuration),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_goal),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_notification),
                onItemClick = { }
            ),
            MenuItem(
                text = stringResource(R.string.menu_logout),
                onItemClick = { }
            ),
        )
        EditTaskScreen(
            onBackButtonPressed = { onBackButtonPressed() },
            onHomeButtonPressed = { onHomeButtonPressed() },
            onNotificationClicked = { onNotificationClicked() },
            menuItems = menuItems,
        )
    }
}

fun NavGraphBuilder.splashScreenRoute(
    onEmailLoginClicked: () -> Unit,
) {
    composable(route = Screen.SplashScreen.route) {
        SplashScreen(
            onEmailLoginClicked = { onEmailLoginClicked() }
        )
    }
}

@Composable
fun getCurrentDestination(navController: NavController): NavDestination? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    return navBackStackEntry?.destination
}
