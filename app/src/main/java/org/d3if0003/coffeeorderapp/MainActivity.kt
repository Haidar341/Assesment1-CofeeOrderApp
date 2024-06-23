package org.d3if0003.coffeeorderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if0003.coffeeorderapp.ui.theme.CoffeeOrderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeOrderAppTheme {
                val navController = rememberNavController()
                val viewModel = remember { CoffeeOrderViewModel() }
                var showInfoDialog by remember { mutableStateOf(false) }

                if (showInfoDialog) {
                    AlertDialog(
                        onDismissRequest = { showInfoDialog = false },
                        title = { Text("Tentang Aplikasi") },
                        text = { Text("Aplikasi Catatan Pencatatan Kopi") },
                        confirmButton = {
                            Button(onClick = { showInfoDialog = false }) {
                                Text("OK")
                            }
                        }
                    )
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Coffee Order App") },
                            actions = {
                                IconButton(onClick = { showInfoDialog = true }) {
                                    Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_info), contentDescription = "Info")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "order_form",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("order_form") {
                            OrderFormScreen(navController, viewModel)
                        }
                        composable("order_list") {
                            OrderListScreen(navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}
