package no.uib.echo.schema

import org.jetbrains.exposed.sql.Table

object UserGroups : Table("user_groups") {
    val userId = uuid("user_id") references Users.id
    val groupId = uuid("group_id") references Groups.id

    override val primaryKey = PrimaryKey(userId, groupId)
}