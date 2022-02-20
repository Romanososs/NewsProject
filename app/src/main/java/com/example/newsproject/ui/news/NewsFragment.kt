package com.example.newsproject.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsproject.databinding.FragmentNewsBinding
import com.example.newsproject.ui.FragmentState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private val TAG = "MyNewsFragment"
    val args: NewsFragmentArgs by navArgs()

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    val viewModel: NewsViewModel by viewModels<NewsViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView called")
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        viewModel.news.observe(viewLifecycleOwner) {
            Log.d(TAG, "News data was changed")
            binding.newsTitle.text = it.title
            binding.newsShortDescription.text = it.shortDescription
            binding.newsPage.loadData(it.fullDescription, "text/html; charset=utf-8", "UTF-8")
        }
        viewModel.state.observe(viewLifecycleOwner) {
            Log.d(TAG, "state was changed to $it")
            binding.layout.visibility = GONE
            binding.stateLoading.stateLoading.visibility = GONE
            binding.stateFailed.stateFailed.visibility = GONE
            when (it) {
                FragmentState.isReady -> binding.layout.visibility = VISIBLE
                FragmentState.isFailed -> {
                    binding.stateFailed.stateFailed.visibility = VISIBLE
                    binding.stateFailed.errorMessage.text = viewModel.errorMessage.value
                }
                else -> binding.stateLoading.stateLoading.visibility = VISIBLE
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView called")
        super.onDestroyView()
        _binding = null
    }
}