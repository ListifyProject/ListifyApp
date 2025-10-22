package com.example.listify.authentication.route

import android.util.Log
import com.example.listify.models.User
import com.example.listify.models.CreateUserRequest
import com.example.listify.models.LoginRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

object AuthApi {
    private const val baseUrl = "http://your-server-ip:8080/api/users"
    private val mapper = jacksonObjectMapper()

    fun registerUser(name: String, email: String, password: String): User? {
        return try {
            val url = URL("$baseUrl/register")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val request = CreateUserRequest(name, email, password)
            mapper.writeValue(connection.outputStream, request)

            if (connection.responseCode == 201) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<User>(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("AuthenticationRoute", "Register error", e)
            null
        }
    }

    fun login(email: String, password: String): User? {
        return try {
            val url = URL("$baseUrl/login")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val request = LoginRequest(email, password)
            mapper.writeValue(connection.outputStream, request)

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<User>(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("AuthenticationRoute", "Login error", e)
            null
        }
    }
}