package lab1

private typealias Matrix = Array<CharArray>

fun Array<CharArray>.toMatrixString(): String {
    val sizeOfBorder = 20

    return "-".repeat(sizeOfBorder) +
            "\n" +
            this.joinToString(separator = "\n") { it.joinToString(separator = " ") } + "\n" +
            "-".repeat(sizeOfBorder) + "\n"
}

internal class MatrixCryptographer(
    val keysForRow: List<Int>,
    val keysForColumn: List<Int>,
) {
    fun encode(str: String): Matrix =
        input(str)
            .encodeRows()
            .encodeColumns()


    fun decode(matrix: Matrix) =
        matrix
            .transpose()
            .decodeRows()
            .decodeColumns()

    fun Matrix.decodeRows(): Matrix {
        val result = this.deepClone()
        this.forEachIndexed { strIndex, str ->
            keysForColumn.forEachIndexed { charIndex, key ->
                result[strIndex][key.keyToIndex()] = this[strIndex][charIndex]
            }
        }
        return result
    }

    fun Matrix.decodeColumns(): Matrix {
        val result = this.deepClone()
        keysForRow.forEachIndexed { indexOfIndex, index ->
            result[index.keyToIndex()] = this[indexOfIndex]
        }
        return result
    }

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

    fun Matrix.encodeRows(): Matrix {
        val result = this.deepClone()
        this.forEachIndexed { strIndex, str ->
            keysForRow.forEachIndexed { charIndex, key ->
                result[strIndex][charIndex] = this[strIndex][key.keyToIndex()]
            }
        }
        return result
    }

    fun Matrix.encodeColumns(): Matrix {
        val result = this.deepClone()
        keysForColumn.forEachIndexed { indexOfIndex, index ->
            result[indexOfIndex] = this[index.keyToIndex()]
        }
        return result
    }

    fun Matrix.transpose(): Matrix {
        val result = this.deepClone()
        this.forEachIndexed { indexStr, str ->
            str.forEachIndexed { indexCh, ch ->
                result[indexStr][indexCh] = this[indexCh][indexStr]
            }
        }
        return result
    }

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