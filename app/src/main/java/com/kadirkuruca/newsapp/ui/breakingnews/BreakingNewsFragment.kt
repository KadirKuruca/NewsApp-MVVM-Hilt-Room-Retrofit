package com.kadirkuruca.newsapp.ui.breakingnews

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kadirkuruca.newsapp.R
import com.kadirkuruca.newsapp.adapter.ArticlesAdapter
import com.kadirkuruca.newsapp.data.model.Article
import com.kadirkuruca.newsapp.databinding.FragmentBreakingNewsBinding
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.*

private const val TAG = "BreakingNewsFragment"
@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news), ArticlesAdapter.OnItemClickListener {

    private val viewModel: BreakingNewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBreakingNewsBinding.bind(view)
        val articleAdapter = ArticlesAdapter(this)

        binding.apply {
            rvBreakingNews.apply {
                adapter = articleAdapter
                setHasFixedSize(true)
            }
        }

        viewModel.breakingNews.observe(viewLifecycleOwner) {
            when(it){
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
                        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    paginationProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onItemClick(article: Article) {
        val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}