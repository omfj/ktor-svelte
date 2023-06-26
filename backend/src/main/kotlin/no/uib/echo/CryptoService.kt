package no.uib.echo

import java.security.MessageDigest

object CryptoService {

    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return digest.fold(StringBuilder()) { sb, it -> sb.append("%02x".format(it)) }.toString()
    }
}