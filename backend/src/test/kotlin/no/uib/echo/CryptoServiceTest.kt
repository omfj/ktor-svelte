package no.uib.echo

import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class CryptoServiceTest {
    @Test
    fun `it hashes passwords correctly`() {
        testPasswords().forEach {
            assertEquals(it.expected, CryptoService.hashPassword(it.password))
        }
    }

    private fun testPasswords() = listOf(
        PasswordTestInput(
            "password",
           "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"
        ),
        PasswordTestInput(
            "123",
            "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"
        ),
        PasswordTestInput(
            "hunter2",
            "f52fbd32b2b3b86ff88ef6c490628285f482af15ddcb29541f94bcf526a3f6c7"
        ),
    )

    private data class PasswordTestInput(
        val password: String,
        val expected: String
    )
}
