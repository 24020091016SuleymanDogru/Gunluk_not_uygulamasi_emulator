package com.example.gunun_sozu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listViewNotes: ListView
    private lateinit var fabAddNote: FloatingActionButton
    private val noteIds = ArrayList<String>()

    companion object {
        const val REQUEST_NOTE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        listViewNotes = findViewById(R.id.listViewNotes)
        fabAddNote = findViewById(R.id.fabAddNote)

        fabAddNote.setOnClickListener {
            startActivityForResult(
                Intent(this, NoteDetailActivity::class.java),
                REQUEST_NOTE
            )
        }

        listViewNotes.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra("NOTE_ID", noteIds[position])
            startActivityForResult(intent, REQUEST_NOTE)
        }

        loadNotes()
    }

    // NoteDetailActivity kapanır kapanmaz bu tetiklenir — anında liste güncellenir
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_NOTE) {
            loadNotes()
        }
    }

    private fun loadNotes() {
        noteIds.clear()
        val items = ArrayList<NoteItem>()
        val cursor = dbHelper.getAllNotes()

        if (cursor.moveToFirst()) {
            do {
                val id      = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID))
                val title   = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TITLE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CONTENT))
                val color   = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_COLOR))

                noteIds.add(id)
                items.add(NoteItem(id, title, content, "Bugün", color ?: "#6C5CE7"))
            } while (cursor.moveToNext())
        }
        cursor.close()

        listViewNotes.adapter = NoteAdapter(this, items)
    }
}