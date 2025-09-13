package lab2

import jdk.internal.org.jline.keymap.KeyMap.key


private typealias Matrix = Array<CharArray>

/**
 * Преобразует матрицу символов в строковое представление с рамкой из дефисов.
 * @receiver Исходная матрица символов.
 * @return Строка с отображением матрицы.
 */
fun Array<CharArray>.toMatrixString(): String {
    val sizeOfBorder = 20

    return "-".repeat(sizeOfBorder) +
            "\n" +
            this.joinToString(separator = "\n") { it.joinToString(separator = " ") } + "\n" +
            "-".repeat(sizeOfBorder) + "\n"
}

/**
 * Создаёт глубокую копию матрицы символов.
 * @receiver Исходная матрица.
 * @return Новая матрица с копиями всех строк.
 */
fun Matrix.deepClone(): Matrix {
    return Array(size) { index -> this[index].copyOf() }
}

internal class Wigener(private val key: String) {
    private val symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    fun String.shiftLeft(n: Int): String {
        val shift = n % length
        return this.substring(shift) + this.substring(0, shift)
    }

    private val matrix = createMatrix()

    private fun createMatrix(): Matrix = Array(symbols.length) { index ->
        symbols
            .shiftLeft(index)
            .toCharArray()
    }.also { println(it.toMatrixString()) }

    fun Char.indexOfSymbol(): Int {
        var result: Int? = null
        symbols.forEachIndexed { index, char ->
            if (char == this) result = index
        }
        return result ?: throw Exception("Index is not found")
    }

    fun encode(str: String): String {
        val repeatedKey = key.repeat(str.length / key.length + 1).substring(0, str.length)
        val result = repeatedKey.toCharArray()
        0.rangeUntil(str.length).forEach { index ->
            result[index] = matrix[repeatedKey[index].indexOfSymbol()][str[index].indexOfSymbol()]
        }
        return result.joinToString(separator = "")
    }
}