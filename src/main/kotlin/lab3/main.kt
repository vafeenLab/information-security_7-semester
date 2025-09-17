package lab3

/*
* Task
* Программа должна вычислять n, φ(n), e, d (при заданных p и q)
* производить шифрование и расшифровку сообщения,
* выводить зашифрованное и расшифрованное сообщение,
* а также вычисляемые параметры.
*
* p=7	q=13	open msg=ALPHA
 */

fun main() {
    val rsa = RSA(p = 7, q = 13)
    val msg = "ALPHA"

    rsa.printParams()

    // Кодируем сообщение
    val encoded = rsa.encode(msg)
    println("Encoded: $encoded")

    // Декодируем сообщение обратно
    val decoded = rsa.decode(encoded)
    println("Decoded: $decoded")
}
