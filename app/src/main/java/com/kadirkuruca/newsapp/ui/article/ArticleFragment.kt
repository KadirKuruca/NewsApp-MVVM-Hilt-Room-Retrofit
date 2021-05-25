package com.kadirkuruca.newsapp.ui.article

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kadirkuruca.newsapp.R
import com.kadirkuruca.newsapp.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels()
    private val args by navArgs<ArticleFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArticleBinding.bind(view)
        binding.apply {
            val article = args.article
            webView.apply {
                webViewClient = WebViewClient()
                article.url?.let {
                    loadUrl(article.url.toString())
                }
            }

            fab.setOnClickListener {
                viewModel.saveArticle(article)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.articleEvent.collect { event ->
                when (event) {
                    is ArticleViewModel.ArticleEvent.ShowArticleSavedMessage -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}