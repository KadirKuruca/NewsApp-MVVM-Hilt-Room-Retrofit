package com.kadirkuruca.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kadirkuruca.newsapp.R
import com.kadirkuruca.newsapp.databinding.FragmentArticleBinding
import com.kadirkuruca.newsapp.ui.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleFragment: Fragment(R.layout.fragment_article) {

    private val viewModel: NewsViewModel by viewModels()
    private val args by navArgs<ArticleFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArticleBinding.bind(view)
        binding.apply {
            val article = args.article
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }
        }
    }
}