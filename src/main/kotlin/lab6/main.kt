package lab6

import lab6.file.FileImportance

/**
 * В ходе лабораторной работы необходимо реализовать программный модуль,
 * создающий матрицу доступа пользователей к объектам доступа
 * и осуществляющий передачу прав доступа.
 *
 */
fun main() {
    // Получаем экземпляр системы управления доступом (реализованной в PC)
    val pc = PC as System

    // Определяем имена файлов
    val fileName1 = "file1"
    val fileName2 = "file2"

    // Добавляем файлы в систему с разной степенью важности
    pc.addFile(fileName1, FileImportance.VeryImportant) // Очень важный файл
    pc.addFile(fileName2, FileImportance.Default)      // Файл обычной важности

    // Добавляем пользователей различных типов
    pc.addAdmin(id = 0, "Admin") // Администратор
    pc.addUser(id = 1, "User")    // Обычный пользователь
    pc.addGuest(id = 2, "Guest")  // Гость

    // Выводим начальное состояние таблицы доступа
    pc.tableToString().also { println(it) }

    // Демонстрация передачи прав: Администратор (id=0) делится правом на запись (Write)
    // на файл fileName1 с Гостем (id=2)
    pc.shareRights(senderId = 0, recipientId = 2, filename = fileName1, access = Access.Write)

    // Пример неуспешной операции: Гость (id=2) пытается поделиться правом на запись (Write)
    // на файл fileName1 с Администратором (id=0).
    // Гость не имеет права на передачу (Share).
    // pc.shareRights(2, 0, fileName1, Access.Write) // операция будет неуспешна

    // Выводим состояние таблицы доступа после передачи прав
    pc.tableToString().also { println(it) }
}