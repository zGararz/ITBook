package com.example.itbook.data.source.local.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.itbook.data.model.Book

class BooksDatabase private constructor(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NANE, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_BOOKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DELETE_BOOKS_TABLE)
        db?.let { onCreate(it) }
    }

    companion object {
        const val DATABASE_NANE = "ITBook.book"
        const val VERSION = 1
        const val TABLE_NAME = "books"
        const val DELETE_BOOKS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        const val CREATE_BOOKS_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "${Book.ISBN13} TEXT PRIMARY KEY,  " +
                    "${Book.TITLE} TEXT, " +
                    "${Book.SUBTITLE}, " +
                    "${Book.IMAGE} TEXT, " +
                    "${Book.URL} TEXT, " +
                    "${Book.AUTHORS} TEXT, " +
                    "${Book.PUBLISHER} TEXT, " +
                    "${Book.LANGUAGE} TEXT, " +
                    "${Book.ISBN10} TEXT, " +
                    "${Book.PAPERS} INT, " +
                    "${Book.YEAR} INT, " +
                    "${Book.RATING} INT, " +
                    "${Book.DESC} TEXT)"

        private var instance: BooksDatabase? = null

        fun getInstance(context: Context) =
            instance ?: BooksDatabase(context).also { instance = it }
    }
}
