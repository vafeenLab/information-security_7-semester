package lab6

import lab6.file.File


internal data class User(
    val id: Int = 0,
    val name: String,
    val type: Type,
    val rights: MutableMap<File, MutableList<Access>> = mutableMapOf(),
) {
    override fun toString(): String {
        return "User(id=$id, name=$name, type=$type)"
    }
}

