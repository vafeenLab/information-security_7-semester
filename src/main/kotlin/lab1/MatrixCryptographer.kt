package lab1

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
 * Класс, реализующий шифрование и дешифрование текста с помощью ключей для строк и колонок.
 * @property keysForRow Список ключей для перестановки символов по строкам.
 * @property keysForColumn Список ключей для перестановки символов по столбцам.
 */
internal class MatrixCryptographer(
    val keysForRow: List<Int>,
    val keysForColumn: List<Int>,
) {
    /**
     * Шифрует входящую строку, преобразуя её в матрицу и применяя кодирование строк и столбцов.
     * @param str Исходная строка для кодирования.
     * @return Закодированная матрица символов.
     */
    fun encode(str: String): Matrix =
        input(str)
            .encodeRows()
            .encodeColumns()

    /**
     * Дешифрует матрицу, выполняя транспонирование, декодирование строк и столбцов.
     * @param matrix Матрица для декодирования.
     * @return Раскодированная матрица символов.
     */
    fun decode(matrix: Matrix) =
        matrix
            .transpose()
            .decodeRows()
            .decodeColumns()

    /**
     * Декодирует строки матрицы на основе ключей для колонок.
     * @receiver Матрица для декодирования.
     * @return Новая матрица с декодированными строками.
     */
    fun Matrix.decodeRows(): Matrix {
        val result = this.deepClone()
        this.forEachIndexed { strIndex, str ->
            keysForColumn.forEachIndexed { charIndex, key ->
                result[strIndex][key.keyToIndex()] = this[strIndex][charIndex]
            }
        }
        return result
    }

    /**
     * Декодирует столбцы матрицы на основе ключей для строк.
     * @receiver Матрица для декодирования.
     * @return Новая матрица с декодированными столбцами.
     */
    fun Matrix.decodeColumns(): Matrix {
        val result = this.deepClone()
        keysForRow.forEachIndexed { indexOfIndex, index ->
            result[index.keyToIndex()] = this[indexOfIndex]
        }
        return result
    }

    /**
     * Преобразует входящую строку в матрицу символов фиксированного размера.
     * @param str Входящая строка для преобразования.
     * @throws Exception Если длина строки не соответствует размеру матрицы.
     * @return Сформированная матрица символов.
     */
    private fun input(str: String): Matrix {
        if (str.length != keysForRow.size * keysForColumn.size) throw Exception("this str cannot be encoded")
        val matrix = Array(keysForRow.size) { CharArray(keysForColumn.size) { 'x' } }
        matrix.indices.forEach { i ->
            val currentMatrixStr = matrix[i]
            currentMatrixStr.indices.forEach { j ->
                matrix[i][j] = str[i * currentMatrixStr.size + j]
            }
            println(currentMatrixStr)
        }
        return matrix
    }

    /**
     * Кодирует строки матрицы, переставляя символы согласно ключам для строк.
     * @receiver Матрица для кодирования.
     * @return Новая матрица с закодированными строками.
     */
    fun Matrix.encodeRows(): Matrix {
        val result = this.deepClone()
        this.forEachIndexed { strIndex, str ->
            keysForRow.forEachIndexed { charIndex, key ->
                result[strIndex][charIndex] = this[strIndex][key.keyToIndex()]
            }
        }
        return result
    }

    /**
     * Кодирует столбцы матрицы, переставляя строки согласно ключам для колонок.
     * @receiver Матрица для кодирования.
     * @return Новая матрица с закодированными столбцами.
     */
    fun Matrix.encodeColumns(): Matrix {
        val result = this.deepClone()
        keysForColumn.forEachIndexed { indexOfIndex, index ->
            result[indexOfIndex] = this[index.keyToIndex()]
        }
        return result
    }

    /**
     * Выполняет транспонирование матрицы.
     * @receiver Матрица для транспонирования.
     * @return Новая транспонированная матрица.
     */
    fun Matrix.transpose(): Matrix {
        val result = this.deepClone()
        this.forEachIndexed { indexStr, str ->
            str.forEachIndexed { indexCh, ch ->
                result[indexStr][indexCh] = this[indexCh][indexStr]
            }
        }
        return result
    }

    /**
     * Преобразует ключ (начинающийся с 1) в индекс массива (начинающийся с 0).
     * @receiver Ключ в системе отсчёта от 1.
     * @return Соответствующий индекс с отсчётом от 0.
     */
    private fun Int.keyToIndex(): Int = this - 1

    /**
     * Создаёт глубокую копию матрицы символов.
     * @receiver Исходная матрица.
     * @return Новая матрица с копиями всех строк.
     */
    fun Matrix.deepClone(): Matrix {
        return Array(size) { index -> this[index].copyOf() }
    }

}
