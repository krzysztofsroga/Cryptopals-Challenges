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
        val blocksCount = cipher.size / blockSize
        var matches = 0

        for (index in 0 until blocksCount) {
            val block = cipher.extractBlock(blockSize, index)
            for (j in index + 1 until blocksCount) {
                val other = cipher.extractBlock(blockSize, j)
                if (block.contentEquals(other))
                    matches++
            }
        }

        return matches
    }

    private fun ByteArray.toSlices(sliceSize: Int): List<ByteArray> {
        return (0..size step sliceSize).map { i ->
            sliceArray(i..min(i + sliceSize, size - 1))
        }

//        val slices = mutableListOf<ByteArray>()
//        for (i in 0..size step sliceSize) {
//            slices += sliceArray(i..min(i + sliceSize, size - 1))
//        }
//        return slices
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