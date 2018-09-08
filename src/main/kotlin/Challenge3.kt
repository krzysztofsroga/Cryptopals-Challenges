class Challenge3(val encodedString: String) {
    private val letterStats = mapOf(
            'a' to 0.08167, 'b' to 0.01492,
            'c' to 0.02782, 'd' to 0.04253,
            'e' to 0.12702, 'f' to 0.02228,
            'g' to 0.02015, 'h' to 0.06094,
            'i' to 0.06966, 'j' to 0.00153,
            'k' to 0.00772, 'l' to 0.04025,
            'm' to 0.02406, 'n' to 0.06749,
            'o' to 0.07507, 'p' to 0.01929,
            'q' to 0.00095, 'r' to 0.05987,
            's' to 0.06327, 't' to 0.09056,
            'u' to 0.02758, 'v' to 0.00978,
            'w' to 0.02360, 'x' to 0.00150,
            'y' to 0.01974, 'z' to 0.00074
    )

    private fun scoreString(string: String) = string.toLowerCase().map { character ->
        letterStats[character] ?: 0.0
    }.sum()

    private fun xorWithCharacter(string: String, character: String): String {
        val code = character.repeat(string.length / character.length)
        return Utils.xorString(string, code)
    }

    class BruteForceResult(val character: String, val string: String, val score: Double)

    fun findBestMatch() {
        val bestMatch = Utils.byteHexCharacters.map { character ->
            val string = Utils.base64(xorWithCharacter(encodedString, character))
            val score = scoreString(string)
            BruteForceResult(character, string, score)
        }.maxBy { result ->
            result.score
        }!!
        println("Best matching character is: ${bestMatch.character} which scored ${bestMatch.score} and produced sentence: '${bestMatch.string}'")
        println("All other strings: ")
        println(
                Utils.byteHexCharacters.map { character ->
                    Utils.base64(xorWithCharacter(encodedString, character))
                }.joinToString(separator = "\n")
        )
    }

}

fun main(args: Array<String>) {
    val challenge = Challenge3("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")
    challenge.findBestMatch()
}