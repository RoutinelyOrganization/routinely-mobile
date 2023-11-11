package com.routinely.routinely.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.routinely.routinely.R
import com.routinely.routinely.changepassword.CreateNewPasswordScreen
import com.routinely.routinely.changepassword.CreateNewPasswordViewModel
import com.routinely.routinely.changepassword.ForgotPasswordScreen
import com.routinely.routinely.changepassword.ForgotPasswordViewModel
import com.routinely.routinely.changepassword.VerificationCodeScreen
import com.routinely.routinely.changepassword.VerificationCodeViewModel
import com.routinely.routinely.home.HomeScreen
import com.routinely.routinely.home.HomeViewModel
import com.routinely.routinely.login.CreateAccountScreen
import com.routinely.routinely.login.CreateAccountViewModel
import com.routinely.routinely.login.LoginScreen
import com.routinely.routinely.login.LoginViewModel
import com.routinely.routinely.splash_screen.SplashScreen
import com.routinely.routinely.task.AddTaskScreen
import com.routinely.routinely.task.AddTaskViewModel
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
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0)
                }
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
            navigateToLoginScreen = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0)
                }
            }
        )
        addTaskScreenRoute(
            onBackButtonPressed = {
                navController.popBackStack()
            },
            onHomeButtonPressed = {
                navController.popBackStack()
//                navController.navigate(Screen.HomeScreen.route)
            },
            navigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            },
            navigateToLoginScreen = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0)
                }
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
            onNotificationClicked = {},
            navigateToLoginScreen = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0)
                }
            }
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

        val createAccountResult by viewModel.createAccountResult.collectAsState()

        CreateAccountScreen(
            onCreateAccountClicked = { userRegister ->
                viewModel.createAccount(userRegister)
            },
            onAlreadyHaveAnAccountClicked = onAlreadyHaveAnAccountClicked,
            createAccountResult = createAccountResult,
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
    navigateToLoginScreen: () -> Unit,
) {
    composable(route = Screen.HomeScreen.route) {
        val viewModel: HomeViewModel = koinViewModel()
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
                onItemClick = {
                    viewModel.logout()
                    navigateToLoginScreen()
                }
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
    onHomeButtonPressed: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToLoginScreen: () -> Unit,
) {
    composable(route = Screen.AddTaskScreen.route) {
        val viewModel: AddTaskViewModel = koinViewModel()
        val shouldGoToNextScreen by viewModel.shouldGoToNextScreen
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
                onItemClick = {
                    viewModel.logout()
                    navigateToLoginScreen()
                }
            ),
        )

        val apiResponse by viewModel.apiResponse.collectAsState()

        AddTaskScreen(
            onBackButtonPressed = onBackButtonPressed,
            onHomeButtonPressed = onHomeButtonPressed,
            navigateToHomeScreen = navigateToHomeScreen,
            onAddTaskClick = { newTask ->
                viewModel.addTask(newTask)
//                viewModel.verifyAllConditions(newTask)
            },
            shouldGoToNextScreen = shouldGoToNextScreen,
//            apiErrorMessage = apiErrorMessage,
            taskNameStateValidation = { taskName ->
                viewModel.taskNameState(taskName)
            },
            taskDateStateValidation = { taskDate ->
                viewModel.taskDateState(taskDate)
            },
            taskTimeStateValidation = { taskTime ->
                viewModel.taskTimeState(taskTime)
            },
//            taskDropdownPriorityStateValidation = { priority ->
//                viewModel.taskPriorityState(priority)
//            },
//            taskDropdownTagsStateValidation = { tag ->
//                viewModel.taskTagState(tag)
//            },
//            taskDropdownCategoryStateValidation = { category ->
//                viewModel.taskCategoryState(category)
//            },
            taskDescriptionStateValidation = { description ->
                viewModel.taskDescriptionState(description)
            },
            menuItems = menuItems,
            addTaskResult = apiResponse,
        )
    }
}

fun NavGraphBuilder.editTaskScreenRoute(
    onBackButtonPressed: () -> Unit,
    onNotificationClicked: () -> Unit,
    onHomeButtonPressed: () -> Unit,
    navigateToLoginScreen: () -> Unit,
) {
    composable(route = Screen.EditTaskScreen.route) {
        val viewModel: AddTaskViewModel = koinViewModel()
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
                onItemClick = {
                    viewModel.logout()
                    navigateToLoginScreen()
                }
            ),
        )
        EditTaskScreen(
            onBackButtonPressed = { onBackButtonPressed() },
            onHomeButtonPressed = { onHomeButtonPressed() },
            onNotificationClicked = { onNotificationClicked() },
            menuItems = menuItems,
            taskNameStateValidation = { taskName ->
                viewModel.taskNameState(taskName)
            },
            taskDateStateValidation = { taskDate ->
                viewModel.taskDateState(taskDate)
            },
            taskTimeStateValidation = { taskTime ->
                viewModel.taskTimeState(taskTime)
            },
//            taskDropdownPriorityStateValidation = { priority ->
//                viewModel.taskPriorityState(priority)
//            },
//            taskDropdownTagsStateValidation = { tag ->
//                viewModel.taskTagState(tag)
//            },
//            taskDropdownCategoryStateValidation = { category ->
//                viewModel.taskCategoryState(category)
//            },
            taskDescriptionStateValidation = { description ->
                viewModel.taskDescriptionState(description)
            },
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
