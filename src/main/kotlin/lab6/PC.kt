package lab6

import lab6.file.File
import lab6.file.FileImportance

/**
 * Объект PC представляет собой реализацию системы управления доступом.
 * Он управляет пользователями, файлами и их правами доступа.
 */
internal object PC : System {
    private var lastId = 0 // Последний использованный ID для пользователей
    private val users = mutableMapOf<Int, User>() // Карта пользователей (ID -> Пользователь)
    private val files = mutableListOf<File>() // Список файлов в системе

    /**
     * Добавляет гостя в систему.
     * @param id ID гостя.
     * @param name Имя гостя.
     */
    override fun addGuest(id: Int, name: String) {
        val guest = User(id = lastId++, name = name, type = Type.Guest).grantAccessToFilesAsGuest()
        users[id] = guest
    }

    /**
     * Добавляет обычного пользователя в систему.
     * @param id ID пользователя.
     * @param name Имя пользователя.
     */
    override fun addUser(id: Int, name: String) {
        val user = User(id = lastId++, name = name, type = Type.User).grantAccessToFilesAsUser()
        users[id] = user
    }

    /**
     * Добавляет администратора в систему.
     * @param id ID администратора.
     * @param name Имя администратора.
     */
    override fun addAdmin(id: Int, name: String) {
        val admin = User(id = lastId++, name = name, type = Type.Admin).grantAccessToFilesAsAdmin()
        users[id] = admin
    }

    /**
     * Добавляет файл в систему.
     * @param name Имя файла.
     * @param importance Важность файла.
     */
    override fun addFile(name: String, importance: FileImportance) {
        files.add(File(name, importance))
    }

    /**
     * Возвращает строковое представление таблицы доступа.
     * Включает информацию о пользователях и их правах на файлы.
     */
    override fun tableToString(): String {
        var result = "\nAccess-Table:" // Заголовок таблицы доступа
        users.forEach { (_, user) -> // Для каждого пользователя в системе
            result += "\nUser: $user \nRights: " // Информация о пользователе
            user.rights.forEach { (file, accessList) -> // Для каждого файла, к которому у пользователя есть права
                result += "| ${file.name} -- ${file.importance} : ${accessList.getAccessStr()} | " // Имя файла, важность и права доступа
            }
            result += "\n"
        }
        return result
    }

    /**
     * Вспомогательная функция для получения строкового представления списка прав доступа.
     * @return "F" для полного доступа, иначе объединенные символы прав.
     */
    private fun List<Access>.getAccessStr(): String {
        if (this.contains(Access.Full)) return "F" // Если есть полный доступ, возвращаем "F"
        return this.joinToString { it.getSymbol() } // Иначе объединяем символы каждого права
    }

    /**
     * Предоставляет гостю права доступа к файлам.
     * Гости получают право на чтение всех файлов.
     */
    private fun User.grantAccessToFilesAsGuest(): User {
        files.forEach { file ->
            this.rights[file] = mutableListOf(Access.Read) // Право на чтение для каждого файла
        }
        return this
    }

    /**
     * Предоставляет обычному пользователю права доступа к файлам.
     * Пользователи получают права на чтение, запись и передачу для файлов с обычной важностью,
     * и только на чтение для файлов с другой важностью.
     */
    private fun User.grantAccessToFilesAsUser(): User {
        files.forEach { file ->
            this.rights[file] = if (file.importance == FileImportance.Default)
                mutableListOf(Access.Read, Access.Write, Access.Share) // Права для файлов обычной важности
            else mutableListOf(Access.Read) // Права для файлов другой важности
        }
        return this
    }

    /**
     * Предоставляет администратору права доступа к файлам.
     * Администраторы получают полный доступ ко всем файлам.
     */
    private fun User.grantAccessToFilesAsAdmin(): User {
        files.forEach { file ->
            this.rights[file] = mutableListOf(Access.Full) // Полный доступ для каждого файла
        }
        return this
    }

    /**
     * Позволяет пользователю поделиться правами на файл с другим пользователем.
     * @param senderId ID пользователя, который делится правами.
     * @param recipientId ID пользователя, которому предоставляются права.
     * @param filename Имя файла.
     * @param access Право доступа, которое передается.
     */
    override fun shareRights(senderId: Int, recipientId: Int, filename: String, access: Access) {
        val sender = users[senderId] // Получаем отправителя
        if (sender == null) {
            println("sender не найден")
            return
        }

        val recipient = users[recipientId] // Получаем получателя
        if (recipient == null) {
            println("recipient не найден")
            return
        }
        val file = files.firstOrNull { it.name == filename } // Находим файл по имени
        if (file == null) {
            println("Файл не найден")
            return
        }
        // Проверяем, имеет ли отправитель право делиться (Share или Full) и обладает ли он передаваемым правом
        if (sender.rights[file]?.let { accesses ->
                (accesses.contains(access) && accesses.contains(Access.Share)) || accesses.contains(Access.Full)
            } == true) {
            recipient.rights[file]?.add(access) // Добавляем право получателю
            if (recipient.rights[file]?.contains(Access.Full) == true || access == Access.Full) { // Если предоставлен полный доступ или уже был
                recipient.rights[file] = mutableListOf(Access.Full)
            } else { // Иначе просто добавляем право, избегая дубликатов
                recipient.rights[file]?.distinct()
            }
        } else {
            println("Операция неуспешна: у отправителя нет необходимых прав для передачи (${access.getSymbol()}) или права на передачу (S).")
        }
    }
}