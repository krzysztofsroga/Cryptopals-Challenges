/**
 * Single-byte XOR cipher
 *
 * The hex encoded string:
 * 1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736
 *
 * ... has been XOR'd against a single character. Find the key, decrypt the message.
 *
 * You can do this by hand. But don't: write code to do it for you.
 *
 * How? Devise some method for "scoring" a piece of English plaintext. Character frequency is a good metric. Evaluate each output and choose the one with the best score.
 */


class Challenge3(private val hexString: HexString) {




    fun findBestMatch(): BruteForceResult? {
        return Utils.byteHexCharacters.map { hexCharacter ->
            val string = Utils.toAsciiString(Utils.xorWithRepeated(hexString, hexCharacter))
            val score = Utils.scoreStringByCharacters(string)
            BruteForceResult(hexCharacter, string, score)
        }.maxBy { result ->
            result.score
        }
    }
}

class BruteForceResult(val sequence: HexString, val string: String, val score: Double)

fun main(args: Array<String>) {
    val challenge = Challenge3(HexString("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))
    val bestMatch  = challenge.findBestMatch()!!
    println("Best matching character is: 0x${bestMatch.sequence} which scored ${bestMatch.score} and produced sentence: '${bestMatch.string}'")
}