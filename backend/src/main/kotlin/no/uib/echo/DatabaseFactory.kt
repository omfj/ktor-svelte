package no.uib.echo

import io.ktor.server.config.ApplicationConfig
import kotlinx.coroutines.Dispatchers
import no.uib.echo.schema.EventRestrictions
import no.uib.echo.schema.Groups
import no.uib.echo.schema.Happenings
import no.uib.echo.schema.Sessions
import no.uib.echo.schema.User
import no.uib.echo.schema.UserGroups
import no.uib.echo.schema.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

val ALL_TABLES = arrayOf(
    Happenings,
    Users,
    Sessions,
    UserGroups,
    Groups,
    EventRestrictions,
)

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.db.driver").getString()
        val jdbcUrl = config.property("storage.db.jdbcUrl").getString()
        val dbName = config.property("storage.db.database").getString()
        val dbUsername = config.property("storage.db.username").getString()
        val dbPassword = config.property("storage.db.password").getString()

        val database = Database.connect(
            url = "$jdbcUrl/$dbName",
            driver = driverClassName,
            user = dbUsername,
            password = dbPassword
        )

        transaction(database) {
            SchemaUtils.drop(*ALL_TABLES)

            SchemaUtils.create(*ALL_TABLES)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}