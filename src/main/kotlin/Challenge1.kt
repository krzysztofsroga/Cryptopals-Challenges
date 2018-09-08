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

class Int12(bits: Int) {
    val first6 = bits shr 6
    val last6 = bits and 0x3f
}

class Challenge1 {
    val testString = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val expectedString = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
    val conversionValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

    fun testConversion() {
        testString.length.let { println(it) }
        println(base64(testString))
    }

//    private fun splitIntoLongs(string: String): Collection<Long> {
//        var list = listOf<Long>()
//        for (i in 0 until string.length step 4) {
//            val a = string.slice(i until i + 4).toLong(16)
//            print(String.format("%02X ", a))
//            list += a
//        }
//        return list
//    }

    private fun base64(string: String): String {
        var outputString = ""
        for (i in 0 until string.length step 3) {
            val int12 = Int12(string.slice(i..i + 2).toInt(16))
            outputString += conversionValues[int12.first6]
            outputString += conversionValues[int12.last6]
        }
        return outputString
    }
}

fun main(args: Array<String>) {
    println("hello world")
    Challenge1().testConversion()
}