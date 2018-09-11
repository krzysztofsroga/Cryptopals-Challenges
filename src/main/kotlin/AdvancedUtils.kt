import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import kotlin.experimental.xor
import java.nio.charset.Charset
import java.util.*

val charset: Charset = StandardCharsets.UTF_8

infix fun String.xorRepeated(toRepeat: String): String {
    val repeated = toRepeat.repeat(length / toRepeat.length + 1).substring(0 until length)
    return this xor repeated
}

infix fun String.xor(other: String): String {
    val first = BigInteger(toByteArray(charset))
    val second = BigInteger(other.toByteArray(charset))

    return (first xor second).toByteArray().toString(charset)
}

fun String.bitCount(): Int = BigInteger(toByteArray(charset)).bitCount()

val Base64String.decoded: String
    get() = Base64.getDecoder().decode(base64).toString(StandardCharsets.UTF_8)

fun <T, R> Collection<T>.mapPairs(transform: (T, T) -> R): List<R> {
    if ((size and 0x01) != 0)
        throw IllegalArgumentException("Argument has odd number of items: $size")
    val list = mutableListOf<R>()
    for (i in 0 until size step 2) {
        list += transform(elementAt(i), elementAt(i + 1))
    }
    return list
}

fun <R> String.mapSlices(sliceLength: Int, transform: (String) -> R): List<R> {
    val list = mutableListOf<R>()
    for (i in 0 until length step sliceLength) {
        list += transform(slice(i until i + sliceLength))
    }
    return list
}
