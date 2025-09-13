package lab3

import java.lang.Math.pow
import kotlin.math.pow
import kotlin.properties.Delegates
import kotlin.text.forEach

internal class RSA(
    private val p: Int,
    private val q: Int
) {
    private val symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    /**
     * Получает индекс символа в алфавите symbols.
     * @receiver Символ для поиска индекса.
     * @return Индекс символа в алфавите.
     * @throws Exception Если символ не найден.
     */
    private fun Char.indexOfSymbol(): Int {
        var result: Int? = null
        symbols.forEachIndexed { index, char ->
            if (char == this) result = index
        }
        return result ?: throw Exception("Index is not found")
    }


    private fun gcd(mInitial: Int, nInitial: Int): Int {
        var m = mInitial
        var n = nInitial
        while (m != n) {
            if (m > n)
                m = m - n
            else n = n - m
        }
        return n
    }

    private fun phi(n: Int) = (p - 1) * (q - 1)

    private fun relativelyPrimeNumber(number: Int): Int {
        var currentNumber = 1
        var result = -1
        while (currentNumber < number) {
            if (gcd(currentNumber, number) == 1)
                result = currentNumber
            currentNumber += 1
        }
        return result
    }

    private var n by Delegates.notNull<Int>()
    private var phi by Delegates.notNull<Int>()
    private var e by Delegates.notNull<Int>()
    private var d by Delegates.notNull<Int>()
    private var publicKeys by Delegates.notNull<Pair<Int, Int>>()
    private var privateKeys by Delegates.notNull<Pair<Int, Int>>()
    private fun d(): Int = (e.toDouble().pow(-1) % phi).toInt()

    private fun evaluateParts() {
        n = p * q
        phi = phi(n)
        e = relativelyPrimeNumber(phi)
        d = d()
        publicKeys = e to n
        privateKeys = d to n
    }

    fun printParams() {
        println("n = $n")
        println("phi = $phi")
        println("e = $e")
        println("d = $d")
        println("publicKeys = $publicKeys")
        println("privateKeys = $privateKeys")
    }


    private fun Int.applyKeys(keys: Pair<Int, Int>): Int =
        (this.toDouble()
            .pow(keys.first.toDouble()) % keys.second).toInt()

    private fun CharArray.applyKeysForMessage(keys: Pair<Int, Int>): String {
        var result = ""
        this.forEach { ch ->
            val index = ch.indexOfSymbol()
            result += " " + index.applyKeys(keys)
        }
        return result
    }

    fun encode(m: String): String {
        evaluateParts()
        printParams()
        return m.toCharArray().applyKeysForMessage(keys = publicKeys)
    }

    fun decode(m: String): String {
        evaluateParts()
        return m.toCharArray().applyKeysForMessage(keys = privateKeys)
    }
}