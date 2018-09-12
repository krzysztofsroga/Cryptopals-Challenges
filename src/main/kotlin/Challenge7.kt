import org.apache.shiro.crypto.AesCipherService
import org.apache.shiro.crypto.OperationMode

import org.apache.shiro.codec.Base64
import org.apache.shiro.crypto.PaddingScheme
import java.util.*
import org.apache.shiro.codec.CodecSupport
import java.io.File
import java.nio.charset.StandardCharsets


class PkcsN7Padding {
//
//    fun padPkcsN7(raw: ByteArray, blockLength: Int): ByteArray {
//        val fullBlocks = raw.size / blockLength
//
//        val paddedLenth = (fullBlocks + 1) * blockLength
//        val padding = paddedLenth - raw.size
//        val result = Arrays.copyOf(raw, paddedLenth)
//        Arrays.fill(result, raw.size, result.size, padding.toByte())
//        return result
//    }

    fun removePadding(raw: ByteArray): ByteArray {
        val paddingLength = raw[raw.size - 1]
        return if (raw.size - paddingLength < 0) raw else Arrays.copyOf(raw, raw.size - paddingLength)

    }

//    @Throws(InvalidPaddingException::class)
//    fun validateAndremovePadding(raw: ByteArray): ByteArray {
//        val paddingLength = raw[raw.size - 1]
//        if (paddingLength < 1)
//            throw InvalidPaddingException()
//
//        var idx = paddingLength
//        while (idx > 0) {
//            if (raw.size < idx)
//                throw InvalidPaddingException()
//
//            if (raw[raw.size - idx] != paddingLength)
//                throw InvalidPaddingException()
//            idx--
//        }
//
//        return removePadding(raw)
//    }
//
//    inner class InvalidPaddingException : RuntimeException()

}

class AESECB {

    private val padding = PkcsN7Padding()
//
//    val blockSize: Int
//        get() = 16

    fun decrypt(ciphertext: ByteArray, key: ByteArray): ByteArray {
        val cipher = AesCipherService()
        cipher.setMode(OperationMode.ECB)
        cipher.setPaddingScheme(PaddingScheme.NONE)

        val plaintext = cipher.decrypt(ciphertext, key)
        return padding.removePadding(plaintext.bytes)
    }
//
//    fun encrypt(plaintext: ByteArray, key: ByteArray): ByteArray {
//        val cipher = AesCipherService()
//        cipher.setMode(OperationMode.ECB)
//        cipher.setPaddingScheme(PaddingScheme.NONE)
//
//        val paddedPlaintext = padding.padPkcsN7(plaintext, blockSize)
//        val ciphertext = cipher.encrypt(paddedPlaintext, key)
//        return ciphertext.bytes
//    }

}

class Challenge7(file: File) {
    val fileData = file.readLines(StandardCharsets.UTF_8).joinToString("")
    fun decryptAES128ECB(key: String): String {
        val aesecb = AESECB()
//        val plaintext = aesecb.decrypt(AdvancedUtils.readAndDecodeFile(file).dropLast(12).toByteArray(StandardCharsets.UTF_8), CodecSupport.toBytes(key))
        val plaintext = aesecb.decrypt(Base64.decode(fileData), CodecSupport.toBytes(key))
        return CodecSupport.toString(plaintext)
    }



}

fun main(args: Array<String>) {
    println(Challenge7(File("src/main/resources/Challenge7.txt")).decryptAES128ECB("YELLOW SUBMARINE"))
}