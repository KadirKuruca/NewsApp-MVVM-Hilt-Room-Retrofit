package com.kadirkuruca.newsapp.repository

import com.kadirkuruca.newsapp.data.local.ArticleDao
import com.kadirkuruca.newsapp.data.model.Article
import com.kadirkuruca.newsapp.data.model.NewsResponse
import com.kadirkuruca.newsapp.data.remote.NewsApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return newsApi.getBreakingNews(countryCode,pageNumber)
    }

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse>{
        return newsApi.searchForNews(searchQuery, pageNumber)
    }

    fun getAllArticles() = articleDao.getArticles()

    suspend fun insertArticle(article: Article) = articleDao.insert(article)

    suspend fun deleteArticle(article: Article) = articleDao.delete(article)

    suspend fun deleteAllArticles() = articleDao.deleteAllArticles()
}