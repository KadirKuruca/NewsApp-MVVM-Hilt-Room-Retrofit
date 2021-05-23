package com.kadirkuruca.newsapp.ui

import androidx.lifecycle.ViewModel
import com.kadirkuruca.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {


}