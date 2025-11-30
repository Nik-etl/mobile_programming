#!/usr/bin/env kotlin

val numbers = (1..100).toList()

val evens = numbers.filter { it % 2 == 0 }
val squares = numbers.map { it * it }
val sum = numbers.reduce { acc, n -> acc + n }


fun main() {
println("Evens: $evens")
println("Squares: $squares")
println("Sum: $sum")
}
