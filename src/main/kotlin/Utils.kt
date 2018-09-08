import kotlin.coroutines.experimental.buildSequence

object Utils {
    class Int12(bits: Int) {
        val first6 = bits shr 6
        val last6 = bits and 0x3f
    }

    const val base64characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    const val hexCharacters = "0123456789abcdef"
    val byteHexCharacters = buildSequence {
        for (i in 0..255) {
            yield(String.format("%02x", i))
        }
    }

    private fun splitIntoLongs(string: String): List<Long> {
        var list = listOf<Long>()
        for (i in 0 .. string.length-4 step 4) {
            list += string.slice(i until i + 4).toLong(16)
        }
        return list
    }

    fun xorList(array1: Collection<Long>, array2: Collection<Long>): List<Long> {
        return (array1 zip array2).map { pair -> pair.first xor pair.second }
    }

    fun xorString(string1: String, string2: String): String {
        return xorList(splitIntoLongs(string1), splitIntoLongs(string2)).joinToString(separator = "") { String.format("%04x", it) }
    }

    fun base64(string: String): String {
        var outputString = ""
        for (i in 0 .. string.length-3 step 3) {
            val int12 = Int12(string.slice(i..i + 2).toInt(16))
            outputString += Utils.base64characters[int12.first6]
            outputString += Utils.base64characters[int12.last6]
        }
        return outputString
    }
}