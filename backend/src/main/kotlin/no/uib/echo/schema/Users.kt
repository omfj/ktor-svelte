package no.uib.echo.schema

import java.time.LocalDateTime
import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import no.uib.echo.CryptoService
import no.uib.echo.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select

@Serializable
data class User(
    @Contextual
    val id: UUID,
    val username: String,
    val email: String,
    val hashedPassword: String,
    @Contextual
    val createdAt: LocalDateTime,
    @Contextual
    val updatedAt: LocalDateTime
)

@Serializable
data class UserProfile(
    val id: String,
    val username: String,
    val email: String,
)

object Users : Table() {
    val id: Column<UUID> = uuid("id").autoGenerate()
    val username: Column<String> = varchar("username", length = 50).uniqueIndex().index("username_index")
    val email: Column<String> = varchar("email", length = 50).uniqueIndex().index("email_index")
    val hashedPassword: Column<String> = varchar("hashedPassword", length = 120)
    val createdAt: Column<LocalDateTime> = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt: Column<LocalDateTime> = datetime("updated_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)

    suspend fun getById(id: UUID): User? = dbQuery {
        this.select { Users.id eq id }.firstOrNull()?.let { toUser(it) }
    }

    suspend fun getBySession(sessionId: UUID): User? = dbQuery {
        val session = Sessions.select { Sessions.id eq sessionId }.firstOrNull() ?: return@dbQuery null

        val userId = session[Sessions.userId]

        return@dbQuery getById(userId)
    }

    suspend fun create(username: String, email: String, hashedPassword: String): User = dbQuery {
        this.insert {
            it[Users.username] = username
            it[Users.email] = email
            it[Users.hashedPassword] = hashedPassword
        } get id

        Users.select { Users.id eq id }.firstOrNull()?.let { toUser(it) }!!
    }

    suspend fun validateUser(username: String, password: String): User? = dbQuery {
        val user = Users.select { Users.username eq username }.singleOrNull() ?: return@dbQuery null

        val hashedPassword = CryptoService.hashPassword(password)

        if (user[Users.hashedPassword] != hashedPassword) {
            return@dbQuery null
        }

        return@dbQuery toUser(user)
    }

    suspend fun userExists(username: String, email: String): Boolean = dbQuery {
        Users.select { Users.username eq username or (Users.email eq email) }.firstOrNull()?.let { toUser(it) } != null
    }

    fun toUser(row: ResultRow): User =
        User(
            id = row[id],
            username = row[username],
            email = row[email],
            hashedPassword = row[hashedPassword],
            createdAt = LocalDateTime.parse(row[createdAt].toString()),
            updatedAt = LocalDateTime.parse(row[updatedAt].toString()),
        )
}
