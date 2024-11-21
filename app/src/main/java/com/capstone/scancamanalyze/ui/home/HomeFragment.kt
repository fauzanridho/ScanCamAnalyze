package com.capstone.scancamanalyze.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.databinding.FragmentHomeBinding
import com.capstone.scancamanalyze.ui.detail.analyze.DetailAnalyzeActivity
import com.capstone.scancamanalyze.ui.welcome.WelcomeActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val binding get() = _binding!!
    private lateinit var analyzeAdapter: AnalyzeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        fetchStories()
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val intent = Intent(requireContext(), WelcomeActivity::class.java)
                startActivity(intent)
            }
        }
        return root

    }

    private fun setupRecyclerView() {
        analyzeAdapter = AnalyzeAdapter(emptyList()) { storyId ->
            Log.d("RecyclerView", "Story ID Clicked: $storyId")
            navigateToDetailAnalyzeActivity(storyId)
        }
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticle.adapter = analyzeAdapter
    }

    private fun fetchStories() {
        viewModel.getStories()

        viewModel.articleList.observe(requireActivity()) { articleResponse ->
            if (articleResponse.error == false) {
                articleResponse.listEvents?.let {
                    analyzeAdapter.article = it
                    analyzeAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error fetching stories: ${articleResponse.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun navigateToDetailAnalyzeActivity(storyId: Int) {
        val intent = Intent(requireContext(), DetailAnalyzeActivity::class.java).apply {
            putExtra("STORY_ID", storyId) // Mengirimkan storyId ke DetailAnalyzeActivity
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}