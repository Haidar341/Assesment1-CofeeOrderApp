package org.d3if0003.coffeeorderapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderFormScreen(navController: NavController, viewModel: CoffeeOrderViewModel) {
    var name by remember { mutableStateOf("") }
    val coffeeOptions = listOf("Espresso", "Long Black", "Cappuccino", "Frappuccino")
    var selectedCoffee by remember { mutableStateOf(coffeeOptions[0]) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Order Coffee") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Customer Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Select Coffee Type", fontSize = 16.sp)
            coffeeOptions.forEach { coffee ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedCoffee == coffee,
                        onClick = { selectedCoffee = coffee }
                    )
                    Text(text = coffee)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colors.error)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(onClick = {
                if (name.isNotBlank()) {
                    viewModel.addOrder(name, selectedCoffee)
                    navController.navigate("order_list") // Pastikan nama tujuan benar
                } else {
                    errorMessage = "Please enter your name"
                }
            }) {
                Text("Submit")
            }
        }
    }
}
