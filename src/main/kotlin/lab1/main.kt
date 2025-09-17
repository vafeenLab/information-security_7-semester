package lab1

/*
* Task:
* k1: 3 1 2 5 4
* k2: 4 5 2 1 3
 */
fun main() {
    val str = "ШИФРОВАНИЕ_ПЕРЕСТАНОВКОЙ_"
    val k1 = "3 1 2 5 4"
    val k2 = "4 5 2 1 3"
    val cryptographer = MatrixCryptographer(
        k1.readKeys(),
        k2.readKeys()
    )
    val encoded = cryptographer.encode(str)
    println(encoded.toMatrixString())
    println(cryptographer.decode(encoded).toMatrixString())
}