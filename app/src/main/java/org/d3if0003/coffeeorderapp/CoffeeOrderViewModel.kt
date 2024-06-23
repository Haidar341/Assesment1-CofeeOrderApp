package org.d3if0003.coffeeorderapp

import androidx.lifecycle.ViewModel

data class CoffeeOrder(val name: String, val coffeeType: String)

class CoffeeOrderViewModel : ViewModel() {
    private val _orders = mutableListOf<CoffeeOrder>()
    val orders: List<CoffeeOrder> get() = _orders

    fun addOrder(name: String, coffeeType: String) {
        _orders.add(CoffeeOrder(name, coffeeType))
    }

    fun removeOrder(order: CoffeeOrder) {
        _orders.remove(order)
    }
}
