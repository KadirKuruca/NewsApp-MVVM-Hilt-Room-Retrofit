package com.kadirkuruca.newsapp.repository

import com.kadirkuruca.newsapp.data.local.ArticleDao
import com.kadirkuruca.newsapp.data.local.ArticleDatabase
import com.kadirkuruca.newsapp.data.remote.NewsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao
) {
}