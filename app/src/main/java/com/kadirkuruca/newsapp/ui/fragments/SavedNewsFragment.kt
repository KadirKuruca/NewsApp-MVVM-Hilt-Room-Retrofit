package com.kadirkuruca.newsapp.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kadirkuruca.newsapp.R
import com.kadirkuruca.newsapp.ui.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {

    private val viewModel: NewsViewModel by viewModels()
}