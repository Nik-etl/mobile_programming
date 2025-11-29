package com.example.scrollable_counter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    private var nextId = 1

    private val _counters = mutableStateListOf<Counter>()
    val counters: List<Counter> = _counters

    init {
        // Start with one counter
        addCounter()
    }

    fun addCounter() {
        _counters.add(Counter(id = nextId, name = "Counter_$nextId"))
        nextId++
    }

    fun removeCounter(id: Int) {
        _counters.removeAll { it.id == id }
    }

    fun increment(id: Int) {
        val index = _counters.indexOfFirst { it.id == id }
        if (index != -1) {
            _counters[index] = _counters[index].copy(value = _counters[index].value + 1)
        }
    }

    fun decrement(id: Int) {
        val index = _counters.indexOfFirst { it.id == id }
        if (index != -1) {
            _counters[index] = _counters[index].copy(value = _counters[index].value - 1)
        }
    }
}