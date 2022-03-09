package com.kadirkuruca.newsapp.ui.searchnews

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadirkuruca.newsapp.data.model.NewsResponse
import com.kadirkuruca.newsapp.repository.NewsRepository
import com.kadirkuruca.newsapp.util.NetworkUtil.Companion.hasInternetConnection
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsResponse: NewsResponse? = null
    var searchNewsPage = 1

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        safeSearchNewCall(searchQuery, searchNewsPage)
    }

    private suspend fun safeSearchNewCall(searchQuery: String, searchNewsPage: Int){
        searchNews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection(context)){
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            }
            else
                searchNews.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null)
                    searchNewsResponse = resultResponse
                else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}