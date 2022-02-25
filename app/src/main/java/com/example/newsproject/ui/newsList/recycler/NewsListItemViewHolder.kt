package com.example.newsproject.ui.newsList.recycler

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.R
import com.example.newsproject.data.News
import com.example.newsproject.ui.ItemClickListener
import kotlinx.datetime.toJavaInstant
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class NewsListItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val titleView: TextView = view.findViewById(R.id.news_title)
    private val dateView: TextView = view.findViewById(R.id.news_date)
    private val shortDesView: TextView = view.findViewById(R.id.news_shortDescription)
    private val dateFormatter = DateTimeFormatter
            .ofPattern("dd.MM.yyyy HH:mm")
            .withZone(ZoneId.from(ZoneOffset.UTC))

    @SuppressLint("SimpleDateFormat")
    fun bind(news: News? = null, lister: ItemClickListener) {
        if (news != null) {
            view.setOnClickListener {
                lister.onItemClicked(news.id)
            }
            titleView.text = news.title
            dateView.text = dateFormatter.format(news.date.toJavaInstant())
            //SimpleDateFormat("dd.MM.yyyy HH:mm").format(news.date)
            shortDesView.text = news.shortDescription
        }
    }
}