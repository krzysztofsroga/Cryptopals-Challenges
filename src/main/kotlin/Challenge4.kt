import java.io.File

class Challenge4(private val file: File) {
    fun findBestMatchingString(): BruteForceResult? {
        val results = mutableListOf<BruteForceResult>()
        file.forEachLine { line ->
            val lineBestMatch = Challenge3(line.asHexString()).findBestMatch()!!
            results += lineBestMatch
        }
        return results.maxBy { result -> result.score }
    }
}

fun main(args: Array<String>) {
    val challenge = Challenge4(File("src/main/resources/Challenge4.txt"))
    val bestMatch = challenge.findBestMatchingString()!!
    println("Best matching character is: 0x${bestMatch.sequence} which scored ${bestMatch.score} and produced sentence: '${bestMatch.string}'")
}