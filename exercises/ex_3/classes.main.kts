#!/usr/bin/env kotlin

open class Employee(val name: String, protected val salary: Int) {
    open fun calculateSalary(): Int {
        return salary
    }
}

class FullTimeEmployee(
    name: String,
    baseSalary: Int,
    private val bonus: Int
) : Employee(name, baseSalary) {
    override fun calculateSalary(): Int {
        return salary + bonus
    }
}

class PartTimeEmployee(
    name: String,
    private val hourlyRate: Int,
    private val hoursWorked: Int
) : Employee(name, 0) {
    override fun calculateSalary(): Int {
        return hourlyRate * hoursWorked
    }
}

// script runs directly
val emp1 = FullTimeEmployee("John", 100100, 25000)
val emp2 = FullTimeEmployee("Jane", 87000, 25000)
val emp3 = PartTimeEmployee("James", 15, 45)

val employees = mapOf(
    emp1.name to emp1,
    emp2.name to emp2,
    emp3.name to emp3
)
for ((name, employee) in employees) {
    println("Salary of $name: ${employee.calculateSalary()}")
}
