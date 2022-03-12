package com.kadirkuruca.newsapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kadirkuruca.newsapp.data.model.Article
import com.kadirkuruca.newsapp.data.model.Source
import com.kadirkuruca.newsapp.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.matchers.Contains

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ArticleDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var articleDatabase: ArticleDatabase
    private lateinit var articleDao: ArticleDao

    @Before
    fun setup() {
        articleDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries().build()

        articleDao = articleDatabase.getArticleDao()

        //Add default value
        val source = Source("sourceName","sourceName")
        val articleToAdd = Article(1, "Author","Content","Description","Publishead At", source, "Title", "Url","UrlToImage")
        runBlockingTest {
            articleDao.insert(articleToAdd)
        }
    }

    @After
    fun teardown(){
        articleDatabase.close()
    }

    @Test
    fun getAllArticles() = runBlockingTest {
        val articleList = articleDao.getArticles().getOrAwaitValue()
        assertThat(articleList).isNotEmpty()
    }

    @Test
    fun insertArticle() = runBlockingTest {
        val source = Source("sourceName","sourceName")
        val articleToAdd = Article(2, "Author","Content","Description","Publishead At", source, "Title", "Url","UrlToImage")
        articleDao.insert(articleToAdd)

        val articleList = articleDao.getArticles().getOrAwaitValue()
        assertThat(articleList).contains(articleToAdd)
    }

    @Test
    fun deleteArticle() = runBlockingTest {
        val source = Source("sourceName","sourceName")
        val article = Article(1, "Author","Content","Description","Publishead At", source, "Title", "Url","UrlToImage")
        val article2 = Article(3, "Author","Content","Description","Publishead At", source, "Title", "Url","UrlToImage")
        articleDao.insert(article)
        articleDao.insert(article2)
        articleDao.delete(article)

        val articleList = articleDao.getArticles().getOrAwaitValue()
        assertThat(articleList).doesNotContain(article)
    }

    @Test
    fun deleteAllArticles() = runBlockingTest {
        val source = Source("sourceName","sourceName")
        val article = Article(2, "Author","Content","Description","Publishead At", source, "Title", "Url","UrlToImage")

        articleDao.insert(article)
        articleDao.deleteAllArticles()

        val articleList = articleDao.getArticles().getOrAwaitValue()
        assertThat(articleList).isEmpty()
    }
}