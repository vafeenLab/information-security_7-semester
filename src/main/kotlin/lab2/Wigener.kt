package lab2

private typealias Matrix = Array<CharArray>

/**
 * Класс для шифрования текста с помощью шифра Виженера.
 * @property key Ключевое слово для шифрования.
 */
internal class Wigener(private val key: String) {
    private val symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    /**
     * Циклически сдвигает строку влево на n символов.
     * @receiver Исходная строка.
     * @param n Количество символов для сдвига.
     * @return Новая строка после сдвига.
     */
    fun String.shiftLeft(n: Int): String {
        val shift = n % length
        return this.substring(shift) + this.substring(0, shift)
    }

    // Создаёт матрицу Виженера (таблицу сдвигов)
    private val matrix = createMatrix()

    private fun createMatrix(): Matrix = Array(symbols.length) { index ->
        symbols
            .shiftLeft(index)
            .toCharArray()
    }//.also { println(it.toMatrixString()) }

    /**
     * Получает индекс символа в алфавите symbols.
     * @receiver Символ для поиска индекса.
     * @return Индекс символа в алфавите.
     * @throws Exception Если символ не найден.
     */
    fun Char.indexOfSymbol(): Int {
        var result: Int? = null
        symbols.forEachIndexed { index, char ->
            if (char == this) result = index
        }
        return result ?: throw Exception("Index is not found")
    }

    /**
     * Кодирует входящую строку с помощью шифра Виженера и ключа.
     * @param str Входящая строка для шифрования.
     * @return Закодированная строка.
     */
    fun encode(str: String): String {
        // Повторяем ключ, чтобы длина совпадала с длиной строки
        val repeatedKey = key.repeat(str.length / key.length + 1).substring(0, str.length)
        val result = repeatedKey.toCharArray()
        0.rangeUntil(str.length).forEach { index ->
            result[index] = matrix[repeatedKey[index].indexOfSymbol()][str[index].indexOfSymbol()]
        }
        return result.joinToString(separator = "")
    }
}
