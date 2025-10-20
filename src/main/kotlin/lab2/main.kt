package lab2

/* Task:
* Текст: SHIFROVANIE
* ключ: ALPHA
 */
fun main() {
    val key = "ALPHA"//"LEMON"
    val text = "SHIFROVANIE"
    println("key=$key")
    println("text=$text")
    val encoded = Wigener(key).encode(text)
    println("encoded text=$encoded")
}