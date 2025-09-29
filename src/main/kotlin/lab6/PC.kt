package lab6

import lab6.file.File
import lab6.file.FileImportance

internal object PC : System {
    private var lastId = 0
    private val users = mutableMapOf<Int, User>()
    private val files = mutableListOf<File>()
    override fun addGuest(id: Int, name: String, password: String) {
        val guest = User(id = lastId++, name = name, type = Type.Guest).grantAccessToFilesAsGuest()
        users.set(id, guest)
    }

    override fun addUser(id: Int, name: String, password: String) {
        val user = User(id = lastId++, name = name, type = Type.User).grantAccessToFilesAsUser()
        users.set(id, user)
    }

    override fun addAdmin(id: Int, name: String, password: String) {
        val admin = User(id = lastId++, name = name, type = Type.Admin).grantAccessToFilesAsAdmin()
        users.set(id, admin)
    }

    override fun addFile(name: String, importance: FileImportance) {
        files.add(File(name, importance))
    }

    override fun tableToString(): String {
        var result = "\nAccess-Table:"
        users.forEach { user ->
            result += "\nUser: $user \nRights: "
            user.value.rights.forEach {
                result += "| ${it.key.name} -- ${it.key.importance} : ${it.value.getAccessStr()} | "
            }
            result += "\n"
        }
        return result
    }

    private fun List<Access>.getAccessStr(): String {
        if (this.contains(Access.Full)) return "F"
        return this.joinToString { it.getSymbol() }
    }

    private fun User.grantAccessToFilesAsGuest(): User {
        files.forEach { file ->
            this.rights.set(file, mutableListOf(Access.Read))
        }
        return this
    }

    private fun User.grantAccessToFilesAsUser(): User {
        files.forEach { file ->
            this.rights.set(
                file, if (file.importance == FileImportance.Default)
                    mutableListOf(Access.Read, Access.Write, Access.Share)
                else mutableListOf(Access.Read)
            )
        }
        return this
    }

    private fun User.grantAccessToFilesAsAdmin(): User {
        files.forEach { file ->
            this.rights.set(file, mutableListOf(Access.Full))
        }
        return this
    }

    override fun shareRights(senderId: Int, recipientId: Int, filename: String, access: Access) {
        val sender = users.get(senderId)
        if (sender == null) {
            println("sender не найден")
            return
        }

        val recipient = users.get(recipientId)
        if (recipient == null) {
            println("recipient не найден")
            return
        }
        val file = files.filter { it.name == filename }.getOrNull(0)
        if (file == null) {
            println("Файл не найден")
            return
        }
        if (sender.rights.get(file)?.let { accesses ->
                accesses.contains(access) && accesses.contains(Access.Share) || accesses.contains(Access.Full)
            } == true) {
            recipient.rights[file]?.add(access)
        } else {
            println("Операция неуспешна")
        }
    }
}