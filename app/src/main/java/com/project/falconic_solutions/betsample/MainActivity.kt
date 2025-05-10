package com.project.falconic_solutions.betsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.falconic_solutions.betsample.data.model.response.Event
import com.project.falconic_solutions.betsample.data.model.response.Outcome
import com.project.falconic_solutions.betsample.ui.state.CartScreenUIState
import com.project.falconic_solutions.betsample.ui.state.DetailScreenUIState
import com.project.falconic_solutions.betsample.ui.state.MainScreenUIState
import com.project.falconic_solutions.betsample.ui.theme.BetSampleTheme
import com.project.falconic_solutions.betsample.ui.view.CartScreen
import com.project.falconic_solutions.betsample.ui.view.EventDetailScreen
import com.project.falconic_solutions.betsample.ui.view.EventScreen
import com.project.falconic_solutions.betsample.ui.viewdata.BottomNavigationItem
import com.project.falconic_solutions.betsample.ui.viewmodel.AnalyticsViewModel
import com.project.falconic_solutions.betsample.ui.viewmodel.CartViewModel
import com.project.falconic_solutions.betsample.ui.viewmodel.DetailViewModel
import com.project.falconic_solutions.betsample.ui.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BetSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    analyticsViewModel: AnalyticsViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val bottomNavigationItemList = listOf(BottomNavigationItem.List, BottomNavigationItem.Cart)

    val mainUiState by eventViewModel.uiState.collectAsState()
    val detailUiState by detailViewModel.uiState.collectAsState()
    val cartUiState by cartViewModel.uiState.collectAsState()

    Scaffold(bottomBar = {
        NavigationBar {
            bottomNavigationItemList.forEachIndexed { index, item ->
                val title = stringResource(id = item.titleResourceId)
                NavigationBarItem(selected = selectedIndex == index, onClick = {
                    selectedIndex = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, label = { Text(text = title) }, icon = {
                    if (item == BottomNavigationItem.Cart) {
                        BadgedBox(badge = {
                            val count = (cartUiState as? CartScreenUIState.Success)?.bets?.count() ?: 0
                            if (count > 0) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                ) {
                                    Text(text = count.toString())
                                }
                            }
                        }) {
                            Icon(imageVector = item.icon, contentDescription = title)
                        }
                    } else {
                        Icon(imageVector = item.icon, contentDescription = title)
                    }
                })
            }
        }
    }) { innerPadding ->
        NavigationHost(navHostController = navController,
            innerPadding = innerPadding,
            mainUiState = mainUiState,
            detailUiState = detailUiState,
            cartUiState = cartUiState,
            onSearch = {
                eventViewModel.filterEvents(it)
            },
            eventClearAction = {
                eventViewModel.clearSearchText()
            },
            cartClearAction = {
                cartViewModel.clearBet()
            },
            detailAction = { id ->
                analyticsViewModel.logScreenEvent("EventDetailScreen")
                navController.navigate(BottomNavigationItem.Detail.createRoute(id))
            },
            getEventOddsAction = { id ->
                detailViewModel.loadEvent(id)
            },
            addAction = { event, bookmakerKey, outcome ->
                val result = cartViewModel.addBet(event, bookmakerKey, outcome)
                analyticsViewModel.logCartEvent("add", result)
            },
            removeAction = {
                cartViewModel.removeBet(it)
                analyticsViewModel.logCartEvent("remove")
            })

    }
}

@Composable
private fun NavigationHost(
    navHostController: NavHostController,
    innerPadding: PaddingValues,
    mainUiState: MainScreenUIState,
    detailUiState: DetailScreenUIState,
    cartUiState: CartScreenUIState,
    onSearch: (String) -> Unit,
    eventClearAction: () -> Unit,
    cartClearAction: () -> Unit,
    detailAction: (String) -> Unit,
    getEventOddsAction: (String) -> Unit,
    addAction: (Event, String, Outcome) -> Unit,
    removeAction: (String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavigationItem.List.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = BottomNavigationItem.List.route) { backstackEntry ->
            EventScreen(uiState = mainUiState, onSearch = onSearch, clearAction = eventClearAction, detailAction = detailAction)
        }
        composable(
            route = BottomNavigationItem.Detail.route, arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { entry ->
            val eventId = entry.arguments?.getString("id") ?: ""
            EventDetailScreen(uiState = detailUiState, id = eventId, getEventOddsAction = getEventOddsAction, addAction = addAction)
        }
        composable(route = BottomNavigationItem.Cart.route) {
            CartScreen(uiState = cartUiState, clearAction = cartClearAction, removeAction = removeAction)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BetSampleTheme {
        MainScreen()
    }
}