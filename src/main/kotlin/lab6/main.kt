package lab6

import lab6.file.FileImportance

/*
* В ходе лабораторной работы необходимо реализовать программный модуль,
* создающий матрицу доступа пользователей к объектам доступа
* и осуществляющий передачу прав доступа
*/
fun main() {
    val pc = PC as System
    val fileName1 = "file1"
    val fileName2 = "file2"
    pc.addFile(fileName1, FileImportance.VeryImportant)
    pc.addFile(fileName2, FileImportance.Default)
    pc.addAdmin(id = 0, "Admin", "Admin")
    pc.addUser(id = 1, "User", "User")
    pc.addGuest(id = 2, "Guest", "Guest")

    pc.tableToString().also { println(it) }

    pc.shareRights(0, 2, fileName1, Access.Write)
    // pc.shareRights(2, 0, fileName1, Access.Write) // операция неуспешна будет


    pc.tableToString().also { println(it) }
}