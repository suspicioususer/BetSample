package com.project.falconic_solutions.betsample.ui.viewdata

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.falconic_solutions.betsample.R

sealed class BottomNavigationItem(val titleResourceId: Int, val route: String, val icon: ImageVector) {
    data object List: BottomNavigationItem(titleResourceId = R.string.bottom_navigation_bar_list, route = "list_screen", icon = Icons.Filled.Home)
    data object Cart: BottomNavigationItem(titleResourceId = R.string.bottom_navigation_bar_cart, route = "cart_screen", icon = Icons.Filled.ShoppingCart)
    data object Detail: BottomNavigationItem(titleResourceId = R.string.bottom_navigation_bar_list, route = "detail_screen/{id}", icon = Icons.Filled.Home) {
        fun createRoute(id: String) = "detail_screen/$id"
    }
}
