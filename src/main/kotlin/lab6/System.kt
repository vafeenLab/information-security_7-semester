package lab6

import lab6.file.FileImportance

internal interface System {
    fun addGuest(id: Int, name: String, password: String)
    fun addUser(id: Int, name: String, password: String)
    fun addAdmin(id: Int, name: String, password: String)
    fun addFile(name: String, importance: FileImportance)
    fun tableToString(): String
    fun shareRights(senderId: Int, recipientId: Int, filename: String, access: Access)
}