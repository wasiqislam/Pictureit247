package com.pictureit24.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pictureit24.R

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val items = mutableListOf<MainItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.feed_item_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        var requestOption: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_broken_image)

        holder.bind(items[position], requestOption)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateContent(newItems: List<MainItem>) {
        items.apply {
            clear()
            addAll(newItems)
            notifyDataSetChanged()
        }
    }


    class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.feed_item_image)
        private val authorTextView = itemView.findViewById<TextView>(R.id.txtAuthorName)

        fun bind(item: MainItem, requestOption: RequestOptions) {
            Glide.with(itemView.context)
                    .load(item.download_url)
                    .apply(requestOption)
                    .into(imageView)

            authorTextView.text = item.author
        }
    }
}
