# Employee Salary Calculator - Kotlin OOP

Demonstrates inheritance and polymorphism with employee salary calculations.

## Classes

**Employee** - Base class with name and salary

**FullTimeEmployee** - Base salary + bonus

**PartTimeEmployee** - Hourly rate × hours worked

## Features

- Inheritance with `open` and `override`
- Polymorphism via `calculateSalary()`
- Map to store employees by name

## Run
```bash
kotlinc employee.kt -include-runtime -d employee.jar && java -jar employee.jar
```

## Output
```
Salary of John: 125100
Salary of Jane: 112000
Salary of James: 675
```
