/**
 * AES in ECB mode
 * The Base64-encoded content in this file has been encrypted via AES-128 in ECB mode under the key
 * "YELLOW SUBMARINE".
 * (case-sensitive, without the quotes; exactly 16 characters; I like "YELLOW SUBMARINE" because it's exactly 16 bytes long, and now you do too).
 * Decrypt it. You know the key, after all.
 * Easiest way: use OpenSSL::Cipher and give it AES-128-ECB as the cipher.
 */

import org.apache.shiro.crypto.AesCipherService
import org.apache.shiro.crypto.OperationMode

import org.apache.shiro.codec.Base64
import org.apache.shiro.crypto.PaddingScheme
import org.apache.shiro.codec.CodecSupport
import java.io.File
import java.nio.charset.StandardCharsets

class Challenge7(file: File) {
    private val fileData = Base64.decode(file.readLines(StandardCharsets.UTF_8).joinToString(""))!!

    fun decrypt(key: String): String {
        val aes = AesCipherService().apply {
            setMode(OperationMode.ECB)
            setPaddingScheme(PaddingScheme.NONE)
        }
        return aes.decrypt(fileData, key.toByteArray(StandardCharsets.UTF_8))
                .bytes
                .dropPadding()
                .toString(StandardCharsets.UTF_8)
    }

    private fun ByteArray.dropPadding() = dropLast(last().toInt()).toByteArray()
}


fun main(args: Array<String>) {
    println(Challenge7(File("src/main/resources/Challenge7.txt")).decrypt("YELLOW SUBMARINE"))
}