import org.apache.shiro.codec.Hex
import java.io.File
import java.nio.charset.StandardCharsets
import kotlin.math.min

object ECBAnalyzer {

    class DetectionResult(val matches: Int, val string: String)

    fun detectEcb(hexCiphers: List<String>, blockSize: Int): String? {
        return hexCiphers.map { DetectionResult(countBlockMatches(Hex.decode(it), blockSize), it) }.maxBy { it.matches }!!.string
    }

    private fun countBlockMatches(cipher: ByteArray, blockSize: Int): Int {
        var matches = 0
        val blocks = cipher.toSlices(blockSize)
        blocks.forEachIndexed { index, block ->
            blocks.forEachIndexed { j, other ->
                if (j > index && block.contentEquals(other))
                    matches++
            }
        }
        return matches
    }

    private fun ByteArray.toSlices(sliceSize: Int): List<ByteArray> {
        return (0 until size step sliceSize).map { i ->
            sliceArray(i until min(i + sliceSize, size - 1))
        }
    }

    private fun ByteArray.extractBlock(blockSize: Int, blockIndex: Int): ByteArray {
        val from = blockSize * blockIndex
        val to = min(from + blockSize, size)
        return sliceArray(from until to)
    }
}

class Challenge8(file: File) {
    private val hexCiphers = file.readLines(StandardCharsets.UTF_8)
    fun detectEcb(blockSize: Int): String {
        return ECBAnalyzer.detectEcb(hexCiphers, blockSize)!!
    }
}

fun main(args: Array<String>) {
    val challenge = Challenge8(File("src/main/resources/Challenge8.txt"))
    val solution = challenge.detectEcb(16)
    println(solution)
}