import org.apache.shiro.codec.Hex
import java.io.File
import java.nio.charset.StandardCharsets
import kotlin.math.min

class Challenge8(file: File) {
    class DetectionResult(val matches: Int, val string: String)

    private val hexStrings = file.readLines(StandardCharsets.UTF_8)

    fun detectEcb(blockSize: Int): String {
        return hexStrings.map { hex ->
            DetectionResult(countBlockMatches(Hex.decode(hex), blockSize), hex)
        }.maxBy { it.matches }!!.string
    }

    private fun countBlockMatches(cipher: ByteArray, blockSize: Int): Int {
        val blocks = cipher.toSlices(blockSize)
        val indexedBlocks = blocks.mapIndexed { index, block -> index to block }
        return indexedBlocks.sumBy { (i, block) ->
            indexedBlocks.count { (j, other) -> (j > i && block.contentEquals(other)) }
        }
    }

    private fun ByteArray.toSlices(sliceSize: Int): List<ByteArray> {
        return (0 until size step sliceSize).map { i ->
            sliceArray(i until min(i + sliceSize, size - 1))
        }
    }

}

fun main(args: Array<String>) {
    val challenge = Challenge8(File("src/main/resources/Challenge8.txt"))
    val solution = challenge.detectEcb(16)
    println(solution)
}