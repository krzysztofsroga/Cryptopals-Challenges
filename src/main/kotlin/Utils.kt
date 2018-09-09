import kotlin.coroutines.experimental.buildSequence

object Utils {

    class Int12(bits: Int) {
        val first6 = bits shr 6
        val last6 = bits and 0x3f
    }

    const val base64characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    val hexCharacters = "0123456789abcdef".asHexString()
    val byteHexCharacters = buildSequence {
        for (i in 0..255) {
            yield(String.format("%02x", i).asHexString())
        }
    }

    private fun splitIntoLongs(string: HexString): List<Long> {
        var list = listOf<Long>()
        for (i in 0 .. string.length-4 step 4) {
            list += string.hex.slice(i until i + 4).toLong(16)
        }
        return list
    }

    fun xorList(array1: Collection<Long>, array2: Collection<Long>): List<Long> {
        return (array1 zip array2).map { pair -> pair.first xor pair.second }
    }

    fun xorHexStrings(hexString1: HexString, hexString2: HexString): HexString {
        return xorList(splitIntoLongs(hexString1), splitIntoLongs(hexString2))
                .joinToString(separator = "") { String.format("%04x", it) }
                .asHexString()
    }

    fun base64(hexString: HexString): Base64String {
        var outputString = ""
        for (i in 0 .. hexString.length-3 step 3) {
            val int12 = Int12(hexString.hex.slice(i..i + 2).toInt(16))
            outputString += Utils.base64characters[int12.first6]
            outputString += Utils.base64characters[int12.last6]
        }
        return outputString.asBase64String()
    }

    fun toAsciiString(hexString: HexString): String {
        return splitIntoChars(hexString).joinToString(separator = "")
    }

    private fun splitIntoChars(hexString: HexString): CharArray {
        var list = listOf<Char>()
        for (i in 0 .. hexString.length - 2 step 2) {
            list += hexString.hex.slice(i..i+1).toInt(16).toChar()
        }
        return list.toCharArray()
    }
}

//inline class HexString(val value: String)