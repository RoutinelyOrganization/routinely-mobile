package com.routinely.routinely.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.routinely.routinely.R
import com.routinely.routinely.changepassword.CreateNewPasswordScreen
import com.routinely.routinely.changepassword.CreateNewPasswordViewModel
import com.routinely.routinely.changepassword.ForgotPasswordScreen
import com.routinely.routinely.changepassword.ForgotPasswordViewModel
import com.routinely.routinely.changepassword.VerificationCodeScreen
import com.routinely.routinely.changepassword.VerificationCodeViewModel
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.ForgotPasswordRequest
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
import com.routinely.routinely.task.EditTaskViewModel
import com.routinely.routinely.ui.components.IndeterminateCircularIndicator
import com.routinely.routinely.util.MenuItem
import com.routinely.routinely.util.TaskItem
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
            navigateToLoginScreen = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0)
                }
            },
            navigateToEditScreen = { taskId ->
                navController.navigate(Screen.EditTaskScreen.withArgs(taskId))
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
            saveUser = { token, remember ->
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
            onUpdatePasswordClicked = { password: String, confirmPassword: String ->
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
    navigateToCodeVerificationScreen: (accountId: String) -> Unit,
) {
    composable(route = Screen.ForgotPasswordScreen.route) {
        val viewModel: ForgotPasswordViewModel = koinViewModel()
        val forgotPasswordResult by viewModel.forgotPasswordResult.collectAsState()

        ForgotPasswordScreen(
            navigateToCodeVerificationScreen = navigateToCodeVerificationScreen,
            onResetPasswordClicked = { forgotPasswordRequest: ForgotPasswordRequest ->
                viewModel.sendEmail(forgotPasswordRequest)
            },
            emailStateValidation = {
                viewModel.emailState(it)
            },
            forgotPasswordResult = forgotPasswordResult
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
            onConfirmResetPasswordClicked = { code: String ->
                viewModel.verifyAllConditions(code)
            },
            codeStateValidation = { code: String ->
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
    navigateToLoginScreen: () -> Unit,
    navigateToEditScreen: (taskId: Int) -> Unit,
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

        val deleteTaskResponse by viewModel.deleteTaskResponse.collectAsStateWithLifecycle()
        val getTasksResponse = viewModel.getTasksResponse.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = deleteTaskResponse) {
            if (deleteTaskResponse == ApiResponse.Success) {
                viewModel.getUserTasks(
                    month = viewModel.lastMonth,
                    year = viewModel.lastYear,
                    day = viewModel.lastDay,
                    force = true
                )
            }
        }

        HomeScreen(
            onNotificationClicked = { onNotificationClicked() },
            onNewTaskClicked = { onNewTaskClicked() },
            onEditTaskClicked = {
                navigateToEditScreen(it.id)
            },
            onDeleteTaskClicked = {
                viewModel.excludeTask(it)
            },
            menuItems = menuItems,
            onSelectDayChange = { month, year, day ->
                viewModel.getUserTasks(month, year, day)
            },
            getTasksResponse = getTasksResponse.value,
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
            },
            taskNameStateValidation = { taskName ->
                viewModel.taskNameState(taskName)
            },
            taskDateStateValidation = { taskDate ->
                viewModel.taskDateState(taskDate)
            },
            taskTimeStateValidation = { taskTime ->
                viewModel.taskTimeState(taskTime)
            },
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
    composable(
        route = Screen.EditTaskScreen.route,
        arguments = listOf(
            navArgument("taskId") { type = NavType.IntType },
        )
    ) { backStackEntry ->
        val viewModel: EditTaskViewModel = koinViewModel()
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

        val taskIdArg = backStackEntry.arguments!!.getInt("taskId")

        val taskItem = remember { mutableStateOf<TaskItem?>(null) }

        LaunchedEffect(taskIdArg) {
            taskItem.value = viewModel.getTaskById(taskIdArg)
        }

        val apiResponse by viewModel.apiResponse.collectAsState()

        if (taskItem.value != null) {
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
                taskDescriptionStateValidation = { description ->
                    viewModel.taskDescriptionState(description)
                },
                editTaskResult = apiResponse,
                initialTask = taskItem.value!!,
                onSaveChanges = { taskId, newTask ->
                    viewModel.saveTask(taskId, newTask)
                },
                onDeleteTask = {
                    viewModel.deleteTask(it)
                },
                onDuplicateTask = {
                    viewModel.duplicateTask()
                }

            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                IndeterminateCircularIndicator()
            }
        }
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
