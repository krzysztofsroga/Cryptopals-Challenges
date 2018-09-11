import java.io.File
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.util.*

class Challenge6(file: File) {
    private val fileData = readAndDecodeFile(file)

    fun breakKeysize(): Int {
        return (2..41).map { keysize -> Pair(keysize, scoreKeysize(keysize)) }.minBy { it.second }!!.first
    }

    private fun scoreKeysize(keysize: Int): Double {
        val first = fileData.slice(0 until keysize)
        val second = fileData.slice(keysize until 2 * keysize)
        return (first xor second).bitCount().toDouble() / keysize
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
    println("Optimal keysize is ${challenge.breakKeysize()}")

}