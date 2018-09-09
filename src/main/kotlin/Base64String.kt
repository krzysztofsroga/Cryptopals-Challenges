class Base64String(val base64: String) {
    override fun toString(): String {
        return base64
    }

    val length = base64.length
    override fun equals(other: Any?): Boolean {
        if (other is Base64String && base64 == other.base64)
            return true
        return super.equals(other)
    }

    override fun hashCode() = base64.hashCode()
}

fun String.asBase64String() = Base64String(this)
