import java.io.File
import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import kotlin.experimental.xor
import java.nio.charset.Charset
import java.util.*
import kotlin.math.min

object AdvancedUtils {
    fun readAndDecodeFile(file: File): String {
        val stringBuilder = java.lang.StringBuilder()
        file.forEachLine { line ->
            stringBuilder.append(Base64String(line).decoded)
        }
        return stringBuilder.toString()
    }
}

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

inline fun <T, R> Collection<T>.mapPairs(transform: (T, T) -> R): List<R> {
    if ((size and 0x01) != 0)
        throw IllegalArgumentException("Argument has odd number of items: $size")
    val list = mutableListOf<R>()
    for (i in 0 until size step 2) {
        list += transform(elementAt(i), elementAt(i + 1))
    }
    return list
}

inline fun <R> String.mapSlices(sliceLength: Int, transform: (String) -> R): List<R> {
    val list = mutableListOf<R>()
    for (i in 0 until length step sliceLength) {
        list += transform(slice(i until i + sliceLength))
    }
    return list
}

fun String.asSlices(sliceLength: Int): List<String> {
    return ( 0 until length step sliceLength).map {i->
        saveSlice(i until i + sliceLength)
    }
//    val list = mutableListOf<String>()
//    for (i in 0 until length step sliceLength) {
//        list += saveSlice(i until i + sliceLength)
//    }
//    return list
}

fun Collection<String>.transposed(): List<String> {
    val out = Array(first().length) { StringBuilder() }
    forEach { str ->
        str.forEachIndexed { i, c ->
            out[i].append(c)
        }
    }
    return out.map { it.toString() }
}

fun String.saveSlice(indices: IntRange): String {
    return slice(indices.start..min(indices.endInclusive, length-1))
}