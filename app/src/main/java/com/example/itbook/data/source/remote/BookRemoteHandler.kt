package com.example.itbook.data.source.remote

import com.example.itbook.data.model.Book
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class BookRemoteHandler() {
    fun getJsonFromUrl(url: String): String {
        val url = URL(url)
        val stringBuilder = StringBuilder()
        var urlOpenConnection: HttpURLConnection? = null
        var inputStreamReader: InputStreamReader? = null
        var bufferedReader: BufferedReader? = null

        try {
            urlOpenConnection = url.openConnection() as HttpURLConnection
            inputStreamReader = InputStreamReader(urlOpenConnection.inputStream)
            bufferedReader = BufferedReader(inputStreamReader)

            bufferedReader.forEachLine { stringBuilder.append(it) }
        } catch (e: Exception) {
            return stringBuilder.toString()
        } finally {
            urlOpenConnection?.disconnect()
            inputStreamReader?.close()
        }
        return stringBuilder.toString()
    }

    fun jsonsToBooks(jsonObject: JSONObject): List<Book> {
        val jsonArray = jsonObject.optJSONArray(Book.BOOKS)
        val books = mutableListOf<Book>()

        jsonArray?.also {
            if (!jsonArray.toString().isBlank()) {
                for (i in 0 until jsonArray.length()) {
                    val bookJson = jsonArray.getJSONObject(i)
                    val book = Book(bookJson)
                    books.add(book)
                }
            }
        } ?: books.add(jsonsToBook(jsonObject))

        return books
    }

    fun jsonsToBook(jsonObject: JSONObject): Book = Book(jsonObject)
}
