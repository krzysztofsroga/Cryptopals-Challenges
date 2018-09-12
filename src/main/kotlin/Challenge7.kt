import org.apache.shiro.crypto.AesCipherService
import org.apache.shiro.crypto.OperationMode

import org.apache.shiro.codec.Base64
import org.apache.shiro.crypto.PaddingScheme
import org.apache.shiro.codec.CodecSupport
import java.io.File
import java.nio.charset.StandardCharsets

class AESECB {
    fun decrypt(ciphertext: ByteArray, key: ByteArray): String {
        val cipher = AesCipherService()
        cipher.setMode(OperationMode.ECB)
        cipher.setPaddingScheme(PaddingScheme.NONE)

        val raw = cipher.decrypt(ciphertext, key).bytes
        return raw.dropLast(raw.last().toInt()).toByteArray().toString(StandardCharsets.UTF_8)
    }
}

class Challenge7(file: File) {
    val fileData = file.readLines(StandardCharsets.UTF_8).joinToString("")
    fun decryptAES128ECB(key: String): String {
        val aesecb = AESECB()
        val plaintext = aesecb.decrypt(Base64.decode(fileData), CodecSupport.toBytes(key))
        return plaintext
    }
}

fun main(args: Array<String>) {
    println(Challenge7(File("src/main/resources/Challenge7.txt")).decryptAES128ECB("YELLOW SUBMARINE"))
}