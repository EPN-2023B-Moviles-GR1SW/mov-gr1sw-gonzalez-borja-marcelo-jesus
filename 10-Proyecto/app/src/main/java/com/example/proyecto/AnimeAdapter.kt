package com.example.proyecto

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class AnimeAdapter(
    private val list: List<Anime>
) : RecyclerView.Adapter<AnimeAdapter.MyViewHolder>() {
    private var animeList: List<Anime> = emptyList()

    fun updateList(newList: List<Anime>) {
        animeList = newList
        notifyDataSetChanged()
        Log.d("AnimeAdapter", "Updated list: $animeList")
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(
        view
    ) {
        val titleTextView: TextView
        val front_pageImagen: ImageView

        init {
            titleTextView = view.findViewById(R.id.tv_title)
            front_pageImagen = view.findViewById(R.id.iv_front_page)
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeAdapter.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.animes_rv,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }


    override fun onBindViewHolder(
        holder: MyViewHolder, position: Int
    ) {
        val animeCurrent = this.list[position]
        holder.titleTextView.text = animeCurrent.title
        Log.d("img",animeCurrent.images.jpg.image_url.toString())
        Picasso.get()
            .load(animeCurrent.images.jpg.image_url)
            .into(holder.front_pageImagen, object : Callback {
                override fun onSuccess() {}

                override fun onError(e: Exception) {
                    Log.d("error",e.toString())
                }
            })
    }

}