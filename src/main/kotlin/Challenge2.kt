/**
 * Fixed XOR
 *
 * Write a function that takes two equal-length buffers and produces their XOR combination.
 *
 * If your function works properly, then when you feed it the string:
 * 1c0111001f010100061a024b53535009181c
 *
 * ... after hex decoding, and when XOR'd against:
 * 686974207468652062756c6c277320657965
 *
 * ... should produce:
 * 746865206b696420646f6e277420706c6179
 */


class Challenge2 {
    val input1 = "1c0111001f010100061a024b53535009181c".asHexString()
    val input2 = "686974207468652062756c6c277320657965".asHexString()
    val expectedOutput = "746865206b696420646f6e277420706c6179".asHexString()

    fun testConversion() {
        val output = Utils.xorHexStrings(input1, input2)

        println("Result: $output")

        if (output != expectedOutput) {
            throw RuntimeException("Expected result: $expectedOutput")
        }
    }
}

fun main(args: Array<String>) {
    Challenge2().testConversion()
}