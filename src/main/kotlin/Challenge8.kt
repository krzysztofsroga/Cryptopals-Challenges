import org.apache.shiro.codec.Hex
import java.io.File
import java.nio.charset.StandardCharsets
import kotlin.math.min

class Challenge8(file: File) {
    private val hexStrings = file.readLines(StandardCharsets.UTF_8)

    fun detectEcb(blockSize: Int): HexString {
        return hexStrings.map { hex ->
            val repeatedCount = countRepeatingBlocks(Hex.decode(hex), blockSize)
            DetectionResult(score = repeatedCount, hex = HexString(hex))
        }.maxBy { it.score }!!.hex
    }

    private fun countRepeatingBlocks(data: ByteArray, blockSize: Int): Int {
        val blocks = data.toSlices(blockSize)
        val indexedBlocks = blocks.mapIndexed { index, block -> index to block }
        return indexedBlocks.sumBy { (i, block) ->
            indexedBlocks.count { (j, other) -> (j > i && block.contentEquals(other)) } /* We count all only once. (i<=j) were already counted */
        }
    }

    private fun ByteArray.toSlices(sliceSize: Int): List<ByteArray> {
        return (0 until size step sliceSize).map { i ->
            sliceArray(i until min(i + sliceSize, size - 1))
        }
    }

    private class DetectionResult(val score: Int, val hex: HexString)

}

fun main(args: Array<String>) {
    val challenge = Challenge8(File("src/main/resources/Challenge8.txt"))
    val solution = challenge.detectEcb(16)
    println(solution)
}