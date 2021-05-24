package com.kadirkuruca.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kadirkuruca.newsapp.R
import com.kadirkuruca.newsapp.adapter.ArticlesAdapter
import com.kadirkuruca.newsapp.data.model.Article
import com.kadirkuruca.newsapp.databinding.FragmentSearchNewsBinding
import com.kadirkuruca.newsapp.ui.NewsViewModel
import com.kadirkuruca.newsapp.util.Resource
import com.kadirkuruca.newsapp.util.SEARCH_NEWS_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SearchNewsFragment"

@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.fragment_search_news),
    ArticlesAdapter.OnItemClickListener {

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchNewsBinding.bind(view)
        val articleAdapter = ArticlesAdapter(this)

        binding.apply {
            rvSearchNews.apply {
                adapter = articleAdapter
                setHasFixedSize(true)
            }
        }

        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    paginationProgressBar.visibility = View.INVISIBLE
                    it.data?.let { newsResponse ->
                        articleAdapter.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    paginationProgressBar.visibility = View.INVISIBLE
                    it.message?.let { message ->
                        Log.e(TAG, "Error: $message")
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    paginationProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onItemClick(article: Article) {
        /*val bundle = Bundle().apply {
            putSerializable("article", article)
        }*/
        val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}