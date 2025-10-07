package lab6

import lab6.file.File

/**
 * Класс данных User представляет пользователя в системе.
 *
 * @property id Уникальный идентификатор пользователя.
 * @property name Имя пользователя.
 * @property type Тип пользователя (см. [Type]). Определяет набор прав по умолчанию.
 * @property rights Карта прав доступа пользователя к файлам. Ключ - объект [File], значение - список прав [Access].
 */
internal data class User(
    val id: Int = 0, // Уникальный идентификатор пользователя, по умолчанию 0
    val name: String, // Имя пользователя
    val type: Type, // Тип пользователя (Администратор, Пользователь, Гость)
    val rights: MutableMap<File, MutableList<Access>> = mutableMapOf(), // Права доступа пользователя к файлам
) {
    /**
     * Возвращает строковое представление объекта User.
     * Не включает информацию о правах для краткости.
     */
    override fun toString(): String {
        return "User(id=$id, name=$name, type=$type)"
    }
}
