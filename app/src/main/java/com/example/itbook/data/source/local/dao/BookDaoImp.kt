package com.example.itbook.data.source.local.dao

import com.example.itbook.data.model.Book
import com.example.itbook.data.source.local.Database.BooksDatabase

class BookDaoImp(booksDatabase: BooksDatabase) : BookDao {
    private val writeableDB = booksDatabase.writableDatabase;
    private val readableDB = booksDatabase.readableDatabase;

    override fun insertBook(book: Book) =
        writeableDB.insert(BooksDatabase.TABLE_NAME, null, book.getContentValue())

    override fun getBook(id: String): Book? {
        var book: Book? = null
        val getQuery = "SELECT * FROM ${BooksDatabase.TABLE_NAME} WHERE ${Book.ISBN13} = ?"
        val selectionArg = arrayOf(id)
        val cursor = readableDB.rawQuery(getQuery, selectionArg)

        cursor.also {
            if (it.moveToFirst()) {
                do {
                    book = Book(it)
                } while (it.moveToNext())
            }
        }

        return book
    }

    override fun getAllBooks(): List<Book> {
        val books = mutableListOf<Book>()
        val getQuery = "SELECT * FROM ${BooksDatabase.TABLE_NAME}"
        val cursor = readableDB.rawQuery(getQuery, null)

        cursor.also {
            if (it.moveToFirst()) {
                do {
                    books.add(Book(it))
                } while (it.moveToNext())
            }
        }
        return books
    }

    override fun deleteBook(id: String): Int =
        writeableDB.delete(
            BooksDatabase.TABLE_NAME,
            "${Book.ISBN13} = ?",
            arrayOf(id)
        )
}
