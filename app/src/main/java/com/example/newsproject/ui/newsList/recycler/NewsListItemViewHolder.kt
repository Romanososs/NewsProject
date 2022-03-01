package com.example.newsproject.ui.newsList.recycler

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.R
import com.example.newsproject.data.News
import com.example.newsproject.ui.ItemClickListener
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsListItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val titleView: TextView = view.findViewById(R.id.news_title)
    private val dateView: TextView = view.findViewById(R.id.news_date)
    private val shortDesView: TextView = view.findViewById(R.id.news_shortDescription)

    fun bind(news: News? = null, lister: ItemClickListener) {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        if (news != null) {
            view.setOnClickListener {
                lister.onItemClicked(news.id)
            }
            titleView.text = news.title
            dateView.text = news.date
            shortDesView.text = news.shortDescription
        }
    }
}