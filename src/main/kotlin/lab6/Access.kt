package lab6

internal enum class Access {
    Full {
        override fun getSymbol(): String = "F"
    },
    Read {
        override fun getSymbol(): String = "R"
    },
    Write {
        override fun getSymbol(): String = "W"
    },
    Share {
        override fun getSymbol(): String = "S"
    };

    abstract fun getSymbol(): String

}
