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



class Challenge1(val testString: HexString) {
    fun convertToBase64(): Base64String {
        return Utils.base64(testString)
    }
}

fun main(args: Array<String>) {
    val expectedString = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t".asBase64String()
    val inputString = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d".asHexString()
    val challenge = Challenge1(inputString)

    val output = challenge.convertToBase64()
    println("Result: $output")

    if (output != expectedString) {
        throw RuntimeException("Expected result: $expectedString")
    }
}