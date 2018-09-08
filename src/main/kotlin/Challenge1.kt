/**
 * Convert hex to base64
 *
 * The string:
 * 49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d
 *
 * Should produce:
 * SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t
 *
 * So go ahead and make that happen. You'll need to use this code for the rest of the exercises.
 */

class Bits12(val first4: Int, val second4: Int, val third4: Int) {
    val first6: Int
        get() = (first4 shl 2) or (second4 shr 2)
    val second6: Int
        get() = ((second4 shl 4) or third4) and 0x3f
}

class Challenge1 {
    val testString = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val expectedString = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
    val conversionValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

    fun testConversion() {
        testString.length.let { println(it) }
        splitIntoLongs(testString)
        println(splitToBase64(testString))
    }

    private fun splitIntoLongs(string: String): Collection<Long> {
        var list = listOf<Long>()
        for (i in 0 until string.length step 4) {
            val a = string.slice(i until i + 4).toLong(16)
            print(String.format("%02X ", a))
            list += a
        }
        return list
    }

    private fun splitToBase64(string: String): String {
        var outputString = ""
        for (i in 0 until string.length step 3) {
            val bits12 = Bits12(string[i].toString().toInt(16), string[i+1].toString().toInt(16), string[i+2].toString().toInt(16))
            outputString += conversionValues[bits12.first6]
            outputString += conversionValues[bits12.second6]
        }
        return outputString
    }
}

fun main(args: Array<String>) {
    println("hello world")
    Challenge1().testConversion()
}