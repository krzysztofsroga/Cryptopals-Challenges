import java.io.File
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.util.*

class BFR(val string: String, val score: Double)


class Challenge6(file: File) {
    private val fileData = readAndDecodeFile(file)

    fun breakKeysize(): Int {
        printBestKeySizes()
        return (2..41).map { keysize -> Pair(keysize, scoreKeysize(keysize)) }.minBy { it.second }!!.first
    }

    fun printBestKeySizes() {
        println((2..41).map { keysize -> Pair(keysize, scoreKeysize(keysize)) }.sortedBy { it.second })
    }

    private fun scoreKeysize(keysize: Int): Double {
        val first = fileData.slice(0 until keysize)
        val second = fileData.slice(keysize until 2 * keysize)
        val third = fileData.slice(2 * keysize until 3 * keysize)
        val fourth = fileData.slice(3 * keysize until 4 * keysize)
        return ((first xor second).bitCount() + (second xor third).bitCount() + (third xor fourth).bitCount()).toDouble() / keysize //TODO use ZipWithNext
    }

    fun solveForKeySize(keysize: Int): String {
        val blocks = fileData.asSlices(keysize).transposed().map {column ->
            (0..255).map { byte ->
                val string = column xorRepeated byte.toChar().toString()
                BFR(string, Utils.scoreStringByCharacters(string))
            }.maxBy { result -> result.score }!!.string
        }.transposed().joinToString(separator = "")

        return blocks
    }

    private fun readAndDecodeFile(file: File): String {
        val stringBuilder = StringBuilder()
        file.forEachLine { line ->
            stringBuilder.append(Base64String(line).decoded)
        }
        return stringBuilder.toString()
    }
}

fun main(args: Array<String>) {
    // Simple test
    val string1 = "this is a test"
    val string2 = "wokka wokka!!!"
    println("The distance between '$string1' and '$string2' is ${(string1 xor string2).bitCount()}")

    val challenge = Challenge6(File("src/main/resources/Challenge6.txt"))
    val bestKeySize = challenge.breakKeysize()
    println("Optimal keysize is $bestKeySize")
//    println(challenge.solveForKeySize(bestKeySize))
    println(challenge.solveForKeySize(29))

//    (2..41).forEach {i->
//        println("Solution for keysize $i:\n${challenge.solveForKeySize(i)}\n\n\n")
//    }
    println(arrayOf(string1, string2).toList().transposed())

}