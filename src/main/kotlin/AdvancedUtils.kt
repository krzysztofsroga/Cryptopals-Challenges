import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import kotlin.experimental.xor

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




    fun convertString(string: String): String {
        val a = BigInteger(string.toByteArray(StandardCharsets.UTF_8))
        val b = a.toByteArray().toString(StandardCharsets.UTF_8)
        return b
    }
}

infix fun String.xorRepeated(toRepeat: String): String {
    val charset = StandardCharsets.UTF_8

    val repeated = toRepeat.repeat(length / toRepeat.length + 1 ).substring(0 until length)

    val first = BigInteger(toByteArray(charset))
    val second = BigInteger(repeated.toByteArray(charset))

    val result = first xor second

    return result.toByteArray().toString(charset)
}