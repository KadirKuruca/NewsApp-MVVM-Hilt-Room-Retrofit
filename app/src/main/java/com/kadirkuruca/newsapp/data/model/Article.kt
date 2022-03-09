package com.kadirkuruca.newsapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kadirkuruca.newsapp.util.DateUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
): Parcelable{
    val formattedPublishedAt : String get() {
        publishedAt?.let {
            return DateUtil.changeDateFormat(publishedAt)
        }
        return ""
    }
}