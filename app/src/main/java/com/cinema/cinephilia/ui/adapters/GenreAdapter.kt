package com.cinema.cinephilia.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cinema.cinephilia.R
import com.cinema.cinephilia.model.Genres

class GenreAdapter(
    private val context: Context,
    private var genreList: ArrayList<Genres>
): RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    fun updateDataSet(genreList: ArrayList<Genres>) {

        this.genreList.clear()
        this.genreList.addAll(genreList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {

        holder.genreTitle.text = genreList[position].name
    }

    override fun getItemCount(): Int {

        return genreList.size
    }

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val genreTitle: TextView = itemView.findViewById(R.id.genreTitle)
    }
}