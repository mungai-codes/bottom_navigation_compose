package com.mungaicodes.navigationapp

import android.graphics.drawable.VectorDrawable
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Frankendroid :
        BottomNavigationScreens("Frankendroid", R.string.frankendroid_route, Icons.Filled.Terrain)

    object Pumpkin :
        BottomNavigationScreens("Pumpkin", R.string.pumpkin_screen_route, Icons.Filled.FoodBank)

    object Ghost :
        BottomNavigationScreens("Ghost", R.string.ghost_screen_route, Icons.Filled.Fireplace)

    object ScaryBag :
        BottomNavigationScreens("ScaryBag", R.string.scary_bag_screen_route, Icons.Filled.Cake)

}
