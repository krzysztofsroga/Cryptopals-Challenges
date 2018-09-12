import java.io.File
import java.lang.StringBuilder

class BFR(val score: Double, val string: String)


class Challenge6(file: File) {
    private val fileData = readAndDecodeFile(file)

    fun breakBestKeySizes(): List<Int> {
        return (2..41).map { keySize -> Pair(keySize, scoreKeysize(keySize)) }.sortedBy { it.second }.map { it.first }
    }

    fun printBestKeySizes() {
        println((2..41).map { keySize -> Pair(keySize, scoreKeysize(keySize)) }.sortedBy { it.second })
    }

    private fun scoreKeysize(keySize: Int): Double {
        val first = fileData.slice(0 until keySize)
        val second = fileData.slice(keySize until 2 * keySize)
        val third = fileData.slice(2 * keySize until 3 * keySize)
        val fourth = fileData.slice(3 * keySize until 4 * keySize)
        return ((first xor second).bitCount() + (second xor third).bitCount() + (third xor fourth).bitCount()).toDouble() / keySize //TODO use ZipWithNext
    }

    fun solveForKeySize(keySize: Int): String {
        return fileData.asSlices(keySize).transposed().map { column ->
            (0..255).map { byte ->
                val string = column xorRepeated byte.toChar().toString()
                BFR(Utils.scoreStringByCharacters(string), string)
            }.maxBy { result -> result.score }!!.string
        }.transposed().joinToString(separator = "")
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
    println("The distance between '$string1' and '$string2' is ${(string1 xor string2).bitCount()}\n")

    val challenge = Challenge6(File("src/main/resources/Challenge6.txt"))
    val bestKeySizes = challenge.breakBestKeySizes().take(5) //takes 5 best results and proceeds with them
    println("Best keySizes are $bestKeySizes\n")
//    println(challenge.solveForKeySize(bestKeySize))
    val bestText = bestKeySizes.map { challenge.solveForKeySize(it) }.map { BFR(Utils.scoreStringByCharacters(it), it) }.sortedBy { it.score }.last().string
    println("Best promising one is:\n\n $bestText")
//    println(challenge.solveForKeySize(29))

//    (2..41).forEach {i->
//        println("Solution for keySize $i:\n${challenge.solveForKeySize(i)}\n\n\n")
//    }

}