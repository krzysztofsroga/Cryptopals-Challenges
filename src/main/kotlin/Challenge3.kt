class Challenge3(val hexString: HexString) {

    private fun xorWithRepeated(string: HexString, toRepeat: HexString): HexString {
        val code = HexString(toRepeat.hex.repeat(string.length / toRepeat.length))
        return Utils.xorHexStrings(string, code)
    }


    fun findBestMatch(): BruteForceResult {
        return Utils.byteHexCharacters.map { hexCharacter ->
            val string = Utils.toAsciiString(xorWithRepeated(hexString, hexCharacter))
            val score = Utils.scoreStringByCharacters(string)
            BruteForceResult(hexCharacter, string, score)
        }.maxBy { result ->
            result.score
        }!!
    }
}

class BruteForceResult(val sequence: HexString, val string: String, val score: Double)

fun main(args: Array<String>) {
    val challenge = Challenge3(HexString("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))
    val bestMatch  = challenge.findBestMatch()
    println("Best matching character is: ${bestMatch.sequence} which scored ${bestMatch.score} and produced sentence: '${bestMatch.string}'")
}