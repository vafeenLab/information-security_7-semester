package lab3

/**
 * Реализация алгоритма шифрования RSA.
 * Класс предоставляет функциональность для кодирования и декодирования сообщений
 * с использованием алгоритма RSA с заданными простыми числами.
 *
 * @constructor Создает экземпляр RSA с указанными простыми числами
 * @param p Первое простое число
 * @param q Второе простое число
 * @throws Exception если не удается найти взаимно простое число с phi
 */
internal class RSA(
    private val p: Long,
    private val q: Long
) {
    /** Алфавит символов для кодирования/декодирования */
    private val symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    /** Модуль n = p * q */
    private val n: Long = p * q

    /** Функция Эйлера φ(n) = (p-1)*(q-1) */
    private val phi: Long = (p - 1) * (q - 1)

    /** Открытая экспонента (взаимно простое с phi) */
    private val e: Long = findRelativelyPrime(phi)

    /** Секретная экспонента (modular inverse от e по модулю phi) */
    private val d: Long = modInverse(e, phi)

    /**
     * Находит индекс символа в алфавите.
     *
     * @param this символ для поиска
     * @return индекс символа в алфавите
     * @throws Exception если символ не найден в алфавите
     */
    private fun Char.indexOfSymbol(): Int {
        val index = symbols.indexOf(this)
        if (index == -1) throw Exception("Symbol $this not found in alphabet")
        return index
    }

    /**
     * Находит взаимно простое число с заданным значением phi.
     * Ищет наименьший кандидат больше 1, который взаимно прост с phi.
     *
     * @param phi значение функции Эйлера
     * @return взаимно простое число с phi
     * @throws Exception если не удается найти взаимно простое число
     */
    private fun findRelativelyPrime(phi: Long): Long {
        var candidate = 2L
        while (candidate < phi) {
            if (gcd(candidate, phi) == 1L) return candidate
            candidate++
        }
        throw Exception("Cannot find relatively prime number")
    }

    /**
     * Вычисляет наибольший общий делитель (НОД) двух чисел.
     * Использует алгоритм Евклида.
     *
     * @param a первое число
     * @param b второе число
     * @return НОД чисел a и b
     */
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

    /**
     * Выполняет быстрое возведение в степень по модулю.
     * Использует алгоритм быстрого возведения в степень.
     *
     * @param base основание
     * @param exponent показатель степени
     * @param modulus модуль
     * @return результат (base^exponent) mod modulus
     */
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

    /**
     * Вычисляет модулярную инверсию числа a по модулю m.
     * Использует расширенный алгоритм Евклида.
     *
     * @param a число для которого ищется инверсия
     * @param m модуль
     * @return modular inverse числа a по модулю m
     */
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

    /**
     * Кодирует сообщение с использованием RSA.
     * Каждый символ сообщения преобразуется в число и шифруется.
     *
     * @param message сообщение для кодирования
     * @return список зашифрованных чисел
     */
    fun encode(message: String): List<Long> {
        return message.map { ch ->
            val m = ch.indexOfSymbol().toLong()
            modPow(m, e, n)
        }
    }

    /**
     * Декодирует зашифрованное сообщение с использованием RSA.
     * Каждое зашифрованное число расшифровывается и преобразуется в символ.
     *
     * @param encoded список зашифрованных чисел
     * @return расшифрованное сообщение
     */
    fun decode(encoded: List<Long>): String {
        return encoded.map { c ->
            val m = modPow(c, d, n).toInt()
            symbols[m]
        }.joinToString("")
    }

    /**
     * Выводит параметры RSA на консоль.
     * Включает простые числа, модуль, функцию Эйлера и экспоненты.
     */
    fun printParams() {
        println("p = $p")
        println("q = $q")
        println("n = $n")
        println("phi = $phi")
        println("e = $e")
        println("d = $d")
    }
}