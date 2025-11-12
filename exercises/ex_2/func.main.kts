//#!/usr/bin/env kotlin
//Practice functions and higher-order functions (filter, map, reduce) in Kotlin.
//
//Task
//Create a list of integers (e.g., 1..100 or any 20+ integers).
//
//Use:
//
//filter { ... } → even numbers
//
//        map { ... } → squares of each number
//
//        reduce { acc, n -> ... } → sum of all numbers
//
//        Print results with clear labels:
//
//
//Evens:
//Squares:
//Sum:
val numbers = (1..100).toList()

val evens = numbers.filter { it % 2 == 0 }
val squares = numbers.map { it * it }
val sum = numbers.reduce { acc, n -> acc + n }

println("Evens: $evens")
println("Squares: $squares")
println("Sum: $sum")