import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import kotlin.experimental.xor
import java.nio.charset.Charset

val charset: Charset = StandardCharsets.UTF_8

object AdvancedUtils {
    infix fun String.xorWithRepeating(toRepeat: String): String {
        val repeatedLength = toRepeat.length
        val result = arrayListOf<Byte>()

        val thisBytes = toByteArray()
        val bytesToRepeat = toRepeat.toByteArray()

        for (i in 0 until length) {
            result += thisBytes[i] xor bytesToRepeat[i%repeatedLength]
        }
        return result.toByteArray().contentToString()
//        val x = ByteBuffer.wrap(this.toByteArray().to).
    }
}

infix fun String.xorRepeated(toRepeat: String): String {
    val repeated = toRepeat.repeat(length / toRepeat.length + 1 ).substring(0 until length)
    return this xor repeated
}

infix fun String.xor(other: String): String {
    val first = BigInteger(toByteArray(charset))
    val second = BigInteger(other.toByteArray(charset))

    return (first xor second).toByteArray().toString(charset)
}