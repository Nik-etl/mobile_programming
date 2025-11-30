fun isPrime(num: Int): Boolean {
    if (num < 2) return false
    for (i in 2 until num) {
        if (num % i == 0) return false
    }
    return true
}

fun findPrimes(count: Int): List<Int> {
    val primes = mutableListOf<Int>()
    var num = 2
    while (primes.size < 50) {
        if (isPrime(num)) primes.add(num)
        num++
    }
    return primes
}

fun main() {
    val primeNumList = findPrimes(50)
    println("First 50 prime numbers:\n$primeNumList")
}
