package com.kadirkuruca.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kadirkuruca.newsapp.R
import com.kadirkuruca.newsapp.adapter.ArticlesAdapter
import com.kadirkuruca.newsapp.data.model.Article
import com.kadirkuruca.newsapp.databinding.FragmentSavedNewsBinding
import com.kadirkuruca.newsapp.ui.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment: Fragment(R.layout.fragment_saved_news), ArticlesAdapter.OnItemClickListener {

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSavedNewsBinding.bind(view)
        val articleAdapter = ArticlesAdapter(this)
        binding.apply {
            rvSavedNews.apply {
                adapter = articleAdapter
                setHasFixedSize(true)
            }
        }
    }

    override fun onItemClick(article: Article) {
        val action = SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}