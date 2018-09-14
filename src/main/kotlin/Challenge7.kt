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
        val aesService = AesCipherService().
        aesService.setMode(OperationMode.ECB)
        aesService.setPaddingScheme(PaddingScheme.NONE)

        val raw = aesService.decrypt(fileData, key.toByteArray(StandardCharsets.UTF_8)).bytes
        return raw.dropLast(raw.last().toInt()).toByteArray().toString(StandardCharsets.UTF_8)
    }
}

fun main(args: Array<String>) {
    println(Challenge7(File("src/main/resources/Challenge7.txt")).decrypt("YELLOW SUBMARINE"))
}