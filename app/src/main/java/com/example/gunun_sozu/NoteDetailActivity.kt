package com.example.gunun_sozu

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var dbHelper: DatabaseHelper
    private var noteId: String? = null
    private var selectedColor: String = "#6C5CE7"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        dbHelper = DatabaseHelper(this)

        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)

        // Geri butonu
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        // Renk seçici
        val colorMap = mapOf(
            R.id.colorDotPurple to "#6C5CE7",
            R.id.colorDotTeal   to "#00CEC9",
            R.id.colorDotPink   to "#FD79A8",
            R.id.colorDotAmber  to "#FDCB6E",
            R.id.colorDotGreen  to "#55EFC4"
        )

        fun updateDotSelection(selectedId: Int) {
            colorMap.keys.forEach { id ->
                val v = findViewById<View>(id)
                if (id == selectedId) { v.scaleX = 1.35f; v.scaleY = 1.35f; v.alpha = 1f }
                else { v.scaleX = 1f; v.scaleY = 1f; v.alpha = 0.5f }
            }
        }

        updateDotSelection(R.id.colorDotPurple)

        colorMap.forEach { (id, hex) ->
            findViewById<View>(id).setOnClickListener {
                selectedColor = hex
                updateDotSelection(id)
            }
        }

        // Not yükle ya da sil butonunu gizle
        if (intent.hasExtra("NOTE_ID")) {
            noteId = intent.getStringExtra("NOTE_ID")
            loadNoteDetails(colorMap) { updateDotSelection(it) }
        } else {
            btnDelete.visibility = View.GONE
        }

        // Kaydet / Güncelle
        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val content = etContent.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "Lütfen bir başlık giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = if (noteId == null) {
                dbHelper.addNote(title, content, selectedColor)
            } else {
                dbHelper.updateNote(noteId!!, title, content, selectedColor)
            }

            if (success) {
                val msg = if (noteId == null) "Not kaydedildi" else "Not güncellendi"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)  // MainActivity'ye "yenile" sinyali
                finish()
            } else {
                Toast.makeText(this, "Hata oluştu", Toast.LENGTH_SHORT).show()
            }
        }

        // Sil
        btnDelete.setOnClickListener {
            if (noteId != null) {
                dbHelper.deleteNote(noteId!!)
                Toast.makeText(this, "Not silindi", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun loadNoteDetails(colorMap: Map<Int, String>, onColorLoaded: (Int) -> Unit) {
        val cursor = dbHelper.getNoteById(noteId!!)
        if (cursor.moveToFirst()) {
            etTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TITLE)))
            etContent.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CONTENT)))

            val savedColor = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_COLOR))
            if (!savedColor.isNullOrEmpty()) {
                selectedColor = savedColor
                val selectedId = colorMap.entries.find { it.value == savedColor }?.key
                    ?: R.id.colorDotPurple
                onColorLoaded(selectedId)
            }
        }
        cursor.close()
    }
}