import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*

class Challenge6(private val file: File) {
    fun breakKeysize() {
        file.forEachLine { line ->
            Base64String(line).decoded
        }
    }
}

fun main(args: Array<String>) {
    // Simple test
    val string1 = "this is a test"
    val string2 = "wokka wokka!!!"
    println("The distance between '$string1' and '$string2' is ${(string1 xor string2).bitCount()}")

    val challenge = Challenge6(File("src/main/resources/Challenge6.txt"))

    val dupa = "eonhudnaoduergadoeu132"
    val encoded_dupa = Base64.getEncoder().encode(dupa.toByteArray()).toString(StandardCharsets.UTF_8).asBase64String()
    println(encoded_dupa.decoded)

}