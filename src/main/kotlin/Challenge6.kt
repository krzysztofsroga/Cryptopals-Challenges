/**
 * There's a file here. It's been base64'd after being encrypted with repeating-key XOR. *
 *
 * Decrypt it.
 *
 * Here's how:
 *
 *     Let KEYSIZE be the guessed length of the key; try values from 2 to (say) 40.
 *     Write a function to compute the edit distance/Hamming distance between two strings. The Hamming distance is just the number of differing bits. The distance between:
 *
 *     this is a test
 *
 *     and
 *
 *     wokka wokka!!!
 *
 *     is 37. Make sure your code agrees before you proceed.
 *     For each KEYSIZE, take the first KEYSIZE worth of bytes, and the second KEYSIZE worth of bytes, and find the edit distance between them. Normalize this result by dividing by KEYSIZE.
 *     The KEYSIZE with the smallest normalized edit distance is probably the key. You could proceed perhaps with the smallest 2-3 KEYSIZE values. Or take 4 KEYSIZE blocks instead of 2 and average the distances.
 *     Now that you probably know the KEYSIZE: break the ciphertext into blocks of KEYSIZE length.
 *     Now transpose the blocks: make a block that is the first byte of every block, and a block that is the second byte of every block, and so on.
 *     Solve each block as if it was single-character XOR. You already have code to do this.
 *     For each block, the single-byte XOR key that produces the best looking histogram is the repeating-key XOR key byte for that block. Put them together and you have the key.
 *
 * This code is going to turn out to be surprisingly useful later on. Breaking repeating-key XOR ("Vigenere") statistically is obviously an academic exercise, a "Crypto 101" thing. But more people "know how" to break it than can actually break it, and a similar technique breaks something much more important.
 */


import java.io.File
import java.lang.StringBuilder

//Brute Force Result
class BFR(val score: Double, val string: String)

class Challenge6(file: File) {
    private val fileData = AdvancedUtils.readAndDecodeFile(file)

    fun breakBestKeySizes(): List<Int> {
        return (2..41).map { keySize -> Pair(keySize, scoreKeysize(keySize)) }.sortedBy { it.second }.map { it.first }
    }

    fun printBestKeySizes() {
        println((2..41).map { keySize -> Pair(keySize, scoreKeysize(keySize)) }.sortedBy { it.second })
    }

    private fun scoreKeysize(keySize: Int): Double {
        val first = fileData.slice(0 until keySize)
        val second = fileData.slice(keySize until 2 * keySize)
        val third = fileData.slice(2 * keySize until 3 * keySize)
        val fourth = fileData.slice(3 * keySize until 4 * keySize)
        return ((first xor second).bitCount() + (second xor third).bitCount() + (third xor fourth).bitCount()).toDouble() / keySize //TODO use ZipWithNext
    }

    fun solveForKeySize(keySize: Int): String {
        return fileData.asSlices(keySize).transposed().map { column ->
            (0..255).map { byte ->
                val string = column xorRepeated byte.toChar().toString()
                BFR(Utils.scoreStringByCharacters(string), string)
            }.maxBy { result -> result.score }!!.string
        }.transposed().joinToString(separator = "")
    }


}

fun main(args: Array<String>) {
    // Simple test
    val string1 = "this is a test"
    val string2 = "wokka wokka!!!"
    println("The distance between '$string1' and '$string2' is ${(string1 xor string2).bitCount()}\n")

    val challenge = Challenge6(File("src/main/resources/Challenge6.txt"))
    val bestKeySizes = challenge.breakBestKeySizes().take(5) //takes 5 best results and proceeds with them
    println("Best keySizes are $bestKeySizes\n")
//    println(challenge.solveForKeySize(bestKeySize))
    val bestText = bestKeySizes.map { challenge.solveForKeySize(it) }.map { BFR(Utils.scoreStringByCharacters(it), it) }.sortedBy { it.score }.last().string
    println("Best promising one is:\n\n $bestText")
//    println(challenge.solveForKeySize(29))
//    (2..41).forEach {i->
//        println("Solution for keySize $i:\n${challenge.solveForKeySize(i)}\n\n\n")
//    }

}