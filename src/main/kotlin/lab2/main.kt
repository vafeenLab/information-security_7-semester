package lab2

/* Task:
* Текст: SHIFROVANIE
* ключ: ALPHA
 */
fun main() {
    val key = "ALPHA"//"LEMON"
    val text = "SHIFROVANIE"
    val encoded = Wigener(key).encode(text)
    println(encoded)
}