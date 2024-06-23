package org.d3if0003.coffeeorderapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderListScreen(navController: NavController, viewModel: CoffeeOrderViewModel) {
    val context = LocalContext.current
    val orders by remember { mutableStateOf(viewModel.orders) }
    var refreshTrigger by remember { mutableStateOf(false) }
    var orderToDelete by remember { mutableStateOf<CoffeeOrder?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order List") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val shareIntent = createShareIntent(viewModel.orders)
                        context.startActivity(Intent.createChooser(shareIntent, null))
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.download),
                contentDescription = "Coffee Image",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(orders) { order ->
                    OrderItem(order, viewModel, onDeleteRequest = { orderToDelete = it })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (orderToDelete != null) {
            ConfirmDeleteDialog(
                order = orderToDelete,
                onConfirm = {
                    viewModel.removeOrder(orderToDelete!!)
                    orderToDelete = null
                    refreshTrigger = !refreshTrigger // Toggle to trigger recomposition
                },
                onDismiss = { orderToDelete = null }
            )
        }
    }

    LaunchedEffect(refreshTrigger) {
        // Dummy effect to recompose when refreshTrigger changes
    }
}

@Composable
fun OrderItem(order: CoffeeOrder, viewModel: CoffeeOrderViewModel, onDeleteRequest: (CoffeeOrder) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Customer: ${order.name}")
            Text(text = "Coffee: ${order.coffeeType}")
        }
        IconButton(onClick = { onDeleteRequest(order) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Composable
fun ConfirmDeleteDialog(order: CoffeeOrder?, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Confirm Deletion") },
        text = { Text("Are you sure you want to delete this order?") },
        confirmButton = {
            Button(
                onClick = { onConfirm() }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text("No")
            }
        }
    )
}

fun createShareIntent(orders: List<CoffeeOrder>): Intent {
    val shareBody = StringBuilder()
    shareBody.append("Coffee Orders:\n")
    for (order in orders) {
        shareBody.append("Customer: ${order.name}, Coffee: ${order.coffeeType}\n")
    }

    return Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareBody.toString())
        type = "text/plain"
    }
}
