package com.mungaicodes.navigationapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.mungaicodes.navigationapp.ui.theme.NavigationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationAppTheme {
                MainScreen()
            }
        }
    }
}

sealed class ScaryAnimation(val animId: Int) {
    object Frankendroid : ScaryAnimation(R.raw.frankensteindroid)
    object Pumpkin : ScaryAnimation(R.raw.jackolantern)
    object Ghost : ScaryAnimation(R.raw.ghost)
    object ScaryBag : ScaryAnimation(R.raw.bag)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Frankendroid,
        BottomNavigationScreens.Pumpkin,
        BottomNavigationScreens.Ghost,
        BottomNavigationScreens.ScaryBag,
    )

    Scaffold(
        bottomBar = {
            SpookyAppBottomNavigation(navController = navController, bottomNavigationItems)
        }
    ) {
        MainScreenNavigationConfiguration(navController = navController)
    }
}


@Composable
fun SpookyAppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation {
        val currentRoute = CurrentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.route
                    )
                },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )

        }

    }
}


@Composable
fun CurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route.toString()
}

@Composable
fun ScaryScreen(
    scaryAnimation: ScaryAnimation
) {
    val context = LocalContext.current
    val customView = remember { LottieAnimationView(context) }
    // Adds view to Compose
    AndroidView(
        { customView },
        modifier = Modifier.background(Color.Black)
    ) { view ->
        // View's been inflated - add logic here if necessary
        with(view) {
            setAnimation(scaryAnimation.animId)
            playAnimation()
            repeatMode = LottieDrawable.REVERSE
        }
    }
}


@Composable
fun MainScreenNavigationConfiguration(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.Frankendroid.route
    ) {
        composable(BottomNavigationScreens.Frankendroid.route) {
            ScaryScreen(ScaryAnimation.Frankendroid)
        }
        composable(BottomNavigationScreens.Pumpkin.route) {
            ScaryScreen(ScaryAnimation.Pumpkin)
        }
        composable(BottomNavigationScreens.Ghost.route) {
            ScaryScreen(ScaryAnimation.Ghost)
        }
        composable(BottomNavigationScreens.ScaryBag.route) {
            ScaryScreen(ScaryAnimation.ScaryBag)
        }
    }
}

