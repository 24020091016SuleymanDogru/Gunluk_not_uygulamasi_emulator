package com.example.gunun_sozu

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "NotesDB.db"
        private const val DATABASE_VERSION = 2  // Versiyon artırıldı — color kolonu eklendi
        const val TABLE_NAME = "notes"
        const val COL_ID = "id"
        const val COL_TITLE = "title"
        const val COL_CONTENT = "content"
        const val COL_COLOR = "color"  // YENİ
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COL_TITLE TEXT, "
                + "$COL_CONTENT TEXT, "
                + "$COL_COLOR TEXT DEFAULT '#6C5CE7')")  // varsayılan mor
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addNote(title: String, content: String, color: String = "#6C5CE7"): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, title)
        contentValues.put(COL_CONTENT, content)
        contentValues.put(COL_COLOR, color)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun getAllNotes(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COL_ID DESC", null)
    }

    fun getNoteById(id: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_ID = ?", arrayOf(id))
    }

    fun updateNote(id: String, title: String, content: String, color: String = "#6C5CE7"): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, title)
        contentValues.put(COL_CONTENT, content)
        contentValues.put(COL_COLOR, color)
        val result = db.update(TABLE_NAME, contentValues, "$COL_ID = ?", arrayOf(id))
        return result > 0
    }

    fun deleteNote(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id))
    }
}