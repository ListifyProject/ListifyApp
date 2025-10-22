package com.example.listify.list.route

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class ListApi {
    private val baseUrl = "http://your-server-ip:8080/api/lists"
    private val mapper = jacksonObjectMapper()

    fun createList(name: String, ownerId: UUID): TaskList? {
        return try {
            val url = URL("$baseUrl/create")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val request = CreateListRequest(name, ownerId)
            mapper.writeValue(connection.outputStream, request)

            if (connection.responseCode == 201) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<TaskList>(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ListApi", "Create list error", e)
            null
        }
    }

    fun getListById(listId: UUID): TaskList? {
        return try {
            val url = URL("$baseUrl/$listId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<TaskList>(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ListApi", "Get list error", e)
            null
        }
    }

    fun getUserLists(userId: UUID): List<TaskList> {
        return try {
            val url = URL("$baseUrl/user/$userId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<List<TaskList>>(response)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("ListApi", "Get user lists error", e)
            emptyList()
        }
    }

    fun addTaskToList(listId: UUID, taskName: String): TaskList? {
        return try {
            val url = URL("$baseUrl/$listId/tasks")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val request = CreateTaskRequest(taskName)
            mapper.writeValue(connection.outputStream, request)

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<TaskList>(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ListApi", "Add task error", e)
            null
        }
    }

    fun toggleTask(listId: UUID, taskId: UUID): TaskList? {
        return try {
            val url = URL("$baseUrl/$listId/tasks/$taskId/toggle")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "PUT"

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<TaskList>(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ListApi", "Toggle task error", e)
            null
        }
    }

    fun deleteTask(listId: UUID, taskId: UUID): TaskList? {
        return try {
            val url = URL("$baseUrl/$listId/tasks/$taskId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<TaskList>(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ListApi", "Delete task error", e)
            null
        }
    }

    fun getSharedLists(userId: UUID): List<TaskList> {
        return try {
            val url = URL("$baseUrl/shared/$userId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                mapper.readValue<List<TaskList>>(response)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("ListApi", "Get shared lists error", e)
            emptyList()
        }
    }
}