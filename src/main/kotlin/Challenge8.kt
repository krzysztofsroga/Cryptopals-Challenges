import java.util.Arrays

import org.apache.shiro.codec.Hex
import java.io.File
import java.nio.charset.StandardCharsets

class ECBAnalyzer {

    private val arrayUtils = ArrayManips()

    fun detectEcb(hexCiphers: List<String>, blockLength: Int): String? {
        var maxMatches = -1
        var result: String? = null
        for (cipher in hexCiphers) {
            val matches = countBlockMatches(Hex.decode(cipher), blockLength)
            if (matches > maxMatches) {
                maxMatches = matches
                result = cipher
            }
        }
        return result
    }

    fun countBlockMatches(cipher: ByteArray, blockLength: Int): Int {
        val blocksCount = cipher.size / blockLength
        var matches = 0

        for (index in 0 until blocksCount) {
            val block = arrayUtils.extractBlock(cipher, blockLength, index)
            for (j in index + 1 until blocksCount) {
                val other = arrayUtils.extractBlock(cipher, blockLength, j)
                if (Arrays.equals(block, other))
                    matches++
            }
        }

        return matches
    }
}

class ArrayManips {
    fun extractBlock(raw: ByteArray, blockLength: Int, blockIndex: Int): ByteArray {
        val from = blockLength * blockIndex
        val to = Math.min(blockLength * (blockIndex + 1), raw.size)
        return Arrays.copyOfRange(raw, from, to)
    }
}


class Challenge8(val hexCiphers: List<String>) {
    fun detectEcb(blockLength: Int): String {
        val detector = ECBAnalyzer()
        return detector.detectEcb(hexCiphers, blockLength)!!
    }
}

fun main(args: Array<String>) {
    val challenge = Challenge8(File("src/main/resources/Challenge8.txt").readLines(StandardCharsets.UTF_8))
    println(challenge.detectEcb(16))
}