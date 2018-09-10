import kotlin.coroutines.experimental.buildSequence

object Utils {

    val letterStats = mapOf(
            'a' to 0.08167, 'b' to 0.01492, 'c' to 0.02782, 'd' to 0.04253,
            'e' to 0.12702, 'f' to 0.02228, 'g' to 0.02015, 'h' to 0.06094,
            'i' to 0.06966, 'j' to 0.00153, 'k' to 0.00772, 'l' to 0.04025,
            'm' to 0.02406, 'n' to 0.06749, 'o' to 0.07507, 'p' to 0.01929,
            'q' to 0.00095, 'r' to 0.05987, 's' to 0.06327, 't' to 0.09056,
            'u' to 0.02758, 'v' to 0.00978, 'w' to 0.02360, 'x' to 0.00150,
            'y' to 0.01974, 'z' to 0.00074
    ) //According to Wikipedia

    val characterStats = mapOf(
            ' ' to 0.1831685753, 'e' to 0.1021787708, 't' to 0.0750999398, 'a' to 0.0655307059,
            'o' to 0.0620055405, 'n' to 0.0570308374, 'i' to 0.0573425524, 's' to 0.0532626738,
            'r' to 0.0497199926, 'h' to 0.0486220925, 'l' to 0.0335616550, 'd' to 0.0335227377,
            'u' to 0.0229520040, 'c' to 0.0226508836, 'm' to 0.0201727037, 'f' to 0.0197180888,
            'w' to 0.0168961396, 'g' to 0.0163586607, 'p' to 0.0150311560, 'y' to 0.0146995463,
            'b' to 0.0127076566, 'v' to 0.0078804815, 'k' to 0.0056916712, 'x' to 0.0014980832,
            'j' to 0.0011440544, 'q' to 0.0008809302, 'z' to 0.0005979301
    ) // from http://www.macfreek.nl/memory/Letter_Distribution#Letter_Frequency

    fun scoreStringByCharacters(string: String) = string.toLowerCase().map { character ->
        Utils.characterStats[character] ?: 0.0
    }.sum()

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

    fun xorWithRepeated(string: HexString, toRepeat: HexString): HexString {
        val code = HexString(toRepeat.hex.repeat(string.length / toRepeat.length))
        return Utils.xorHexStrings(string, code)
    }

    private fun splitIntoLongs(string: HexString): List<Long> {
        var list = listOf<Long>()
        for (i in 0..string.length - 4 step 4) {
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
        for (i in 0..hexString.length - 3 step 3) {
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
        for (i in 0..hexString.length - 2 step 2) {
            list += hexString.hex.slice(i..i + 1).toInt(16).toChar()
        }
        return list.toCharArray()
    }
}

//inline class HexString(val value: String)