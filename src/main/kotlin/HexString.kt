class HexString(val hex: String) {
    override fun toString(): String {
        return hex
    }
    val length = hex.length
    override fun equals(other: Any?): Boolean {
        if (other is HexString && hex == other.hex)
            return true
        return super.equals(other)
    }

    override fun hashCode() = hex.hashCode()

}
fun String.asHexString() = HexString(this)
