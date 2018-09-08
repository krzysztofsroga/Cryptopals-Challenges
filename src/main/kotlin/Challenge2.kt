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
    val input1 = "1c0111001f010100061a024b53535009181c"
    val input2 = "686974207468652062756c6c277320657965"
    val expectedOutput = "746865206b696420646f6e277420706c6179"

    fun testConversion() {
        val bytes1 = splitIntoLongs(input1)
        val bytes2 = splitIntoLongs(input2)
        var output = ""
        (bytes1 zip bytes2).map { pair -> pair.first xor pair.second }.forEach {
            output += String.format("%02x", it)
        }
        println("Result: $output")
        if (output != expectedOutput) {
            throw RuntimeException("Expected result: $expectedOutput")
        }
    }

    private fun splitIntoLongs(string: String): Collection<Long> {
        var list = listOf<Long>()
        for (i in 0 until string.length step 4) {
            list += string.slice(i until i + 4).toLong(16)
        }
        return list
    }

}

fun main(args: Array<String>) {
    Challenge2().testConversion()
}