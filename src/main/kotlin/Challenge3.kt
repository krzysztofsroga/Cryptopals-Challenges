class Challenge3(val hexString: HexString) {


    private fun scoreString(string: String) = string.toLowerCase().map { character ->
        Utils.characterStats[character] ?: 0.0
    }.sum()

    private fun xorWithRepeated(string: HexString, toRepeat: HexString): HexString {
        val code = HexString(toRepeat.hex.repeat(string.length / toRepeat.length))
        return Utils.xorHexStrings(string, code)
    }

    class BruteForceResult(val character: HexString, val string: String, val score: Double)

    fun findBestMatch() {
        val bestMatch = Utils.byteHexCharacters.map { hexCharacter ->
            val string = Utils.toAsciiString(xorWithRepeated(hexString, hexCharacter))
            val score = scoreString(string)
            BruteForceResult(hexCharacter, string, score)
        }.maxBy { result ->
            result.score
        }!!
        println("Best matching character is: ${bestMatch.character} which scored ${bestMatch.score} and produced sentence: '${bestMatch.string}'")
    }

}

fun main(args: Array<String>) {
    val challenge = Challenge3(HexString("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))
    challenge.findBestMatch()
}