package com.example.listify.user.route

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class UserApi {
    private val baseUrl = "http://your-server-ip:8080/api/users"
    private val mapper = jacksonObjectMapper()

    fun addAccessibleList(userId: UUID, listId: UUID): User? {
        return try {
            val url = URL("$baseUrl/$userId/accessible-lists/$listId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<User>(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("UserApi", "Add list error", e)
            null
        }
    }

    fun userHasAccessToList(userId: UUID, listId: UUID): Boolean {
        return try {
            val url = URL("$baseUrl/$userId/has-access/$listId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                response.toBoolean()
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("UserApi", "Check access error", e)
            false
        }
    }
}