package com.example.gunun_sozu

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class NoteAdapter(
    private val context: Context,
    private val notes: List<NoteItem>
) : BaseAdapter() {

    override fun getCount() = notes.size
    override fun getItem(position: Int) = notes[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_note, parent, false)

        val note = notes[position]

        view.findViewById<TextView>(R.id.tvNoteTitle).text = note.title
        view.findViewById<TextView>(R.id.tvNotePreview).text = note.content
        view.findViewById<TextView>(R.id.tvNoteDate).text = note.date

        // Soldaki renkli accent çizgisine kaydedilmiş rengi uygula
        try {
            view.findViewById<View>(R.id.viewAccent)
                .setBackgroundColor(Color.parseColor(note.color))
        } catch (e: Exception) {
            view.findViewById<View>(R.id.viewAccent)
                .setBackgroundColor(Color.parseColor("#6C5CE7"))
        }

        return view
    }
}

data class NoteItem(
    val id: String,
    val title: String,
    val content: String,
    val date: String,
    val color: String = "#6C5CE7"
)