fun main() {
    println("=== PART 1: First 50 Prime Numbers ===")
    val primeList = mutableListOf<Int>()
    var number = 2
    
    while (primeList.size < 50) {
        if (isPrime(number)) {
            primeList.add(number)
        }
        number++
    }
    
    println("First 50 prime numbers:")
    println(primeList)
    println("Count: ${primeList.size}")
    
    //create list of first 50 even numbers
    val evenList = mutableListOf<Int>()
    for (i in 1..50) {
        evenList.add(i * 2)
    }
    
    println("\nFirst 50 even numbers:")
    println(evenList)
    println("Count: ${evenList.size}")
    
    //add even numbers to prime list
    primeList.addAll(evenList)
    
    println("\n=== Combined List ===")
    println(primeList)
    println("Total count: ${primeList.size}")
}

fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    
    val sqrt = Math.sqrt(n.toDouble()).toInt()
    for (i in 3..sqrt step 2) {
        if (n % i == 0) return false
    }
    return true
}
