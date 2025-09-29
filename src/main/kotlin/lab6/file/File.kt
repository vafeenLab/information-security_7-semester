package lab6.file

/**
 * Класс данных File представляет файл в системе.
 * Каждый файл характеризуется именем и степенью важности.
 *
 * @property name Имя файла. Уникально идентифицирует файл в рамках данной реализации.
 * @property importance Важность файла (см. [FileImportance]). Влияет на права доступа по умолчанию.
 */
internal data class File(
    val name: String, // Имя файла
    val importance: FileImportance // Важность файла (например, обычный, важный, очень важный)
)