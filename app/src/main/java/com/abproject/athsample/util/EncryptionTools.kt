package com.abproject.athsample.util

import android.content.Context
import android.util.Base64
import com.kazakago.cryptore.CipherAlgorithm
import com.kazakago.cryptore.Cryptore

class EncryptionTools(
    context: Context
) {

    private enum class Alias(val value: String) {
        RSA("CIPHER_RSA"),
        AES("CIPHER_AES")
    }

    private val cryptoreRSA: Cryptore by lazy {
        val builder = Cryptore.Builder(alias = Alias.RSA.value, type = CipherAlgorithm.RSA)
        builder.context = context
        builder.build()
    }

    fun encryptRSA(plainStr: String): String {
        try {
            val plainByte = plainStr.toByteArray()
            val result = cryptoreRSA.encrypt(plainByte = plainByte)
            return Base64.encodeToString(result.bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
//            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
        return ""
    }

    fun decryptRSA(encryptedStr: String): String {
        try {
            val encryptedByte = Base64.decode(encryptedStr, Base64.DEFAULT)
            val result = cryptoreRSA.decrypt(encryptedByte = encryptedByte)
            return String(result.bytes)
        } catch (e: Exception) {
            e.printStackTrace()
//            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
        return ""
    }
}