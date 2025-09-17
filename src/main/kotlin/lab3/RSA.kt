package lab3

internal class RSA(
    private val p: Long,
    private val q: Long
) {
    private val symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private val n: Long = p * q
    private val phi: Long = (p - 1) * (q - 1)
    private val e: Long = findRelativelyPrime(phi)
    private val d: Long = modInverse(e, phi)

    private fun Char.indexOfSymbol(): Int {
        val index = symbols.indexOf(this)
        if (index == -1) throw Exception("Symbol $this not found in alphabet")
        return index
    }

    // Нахождение взаимно простого числа с phi (наименьший кандидат > 1)
    private fun findRelativelyPrime(phi: Long): Long {
        var candidate = 2L
        while (candidate < phi) {
            if (gcd(candidate, phi) == 1L) return candidate
            candidate++
        }
        throw Exception("Cannot find relatively prime number")
    }

    private fun gcd(a: Long, b: Long): Long {
        var x = a
        var y = b
        while (y != 0L) {
            val temp = y
            y = x % y
            x = temp
        }
        return x
    }

    // Быстрое возведение в степень по модулю
    private fun modPow(base: Long, exponent: Long, modulus: Long): Long {
        var result = 1L
        var b = base % modulus
        var e = exponent
        while (e > 0) {
            if (e and 1L == 1L) {
                result = (result * b) % modulus
            }
            e = e shr 1
            b = (b * b) % modulus
        }
        return result
    }

    // Расширенный алгоритм Евклида для вычисления modular inverse
    private fun modInverse(a: Long, m: Long): Long {
        val m0 = m
        var y = 0L
        var x = 1L
        var aa = a
        var mm = m

        if (m == 1L) return 0

        while (aa > 1) {
            val q = aa / mm
            var t = mm

            mm = aa % mm
            aa = t
            t = y

            y = x - q * y
            x = t
        }
        if (x < 0) x += m0
        return x
    }

    fun encode(message: String): List<Long> {
        return message.map { ch ->
            val m = ch.indexOfSymbol().toLong()
            modPow(m, e, n)
        }
    }

    fun decode(encoded: List<Long>): String {
        return encoded.map { c ->
            val m = modPow(c, d, n).toInt()
            symbols[m]
        }.joinToString("")
    }

    fun printParams() {
        println("p = $p")
        println("q = $q")
        println("n = $n")
        println("phi = $phi")
        println("e = $e")
        println("d = $d")
    }
}
