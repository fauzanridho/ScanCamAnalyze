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
import com.capstone.scancamanalyze.adapter.AnalyzeAdapter
import com.capstone.scancamanalyze.databinding.FragmentHomeBinding
import com.capstone.scancamanalyze.ui.detail.analyze.DetailAnalyzeActivity
import com.capstone.scancamanalyze.ui.home.malamhari.MalamHariActivity
import com.capstone.scancamanalyze.ui.home.pagihari.PagiHariActivity
import com.capstone.scancamanalyze.ui.home.product.ProductActivity
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
        viewModel.fetchAnalyzeHistory()

        viewModel.analyzeList.observe(viewLifecycleOwner) { analyzeData ->
            Log.d("HomeFragment", "Analyze data received: $analyzeData")
            if (analyzeData.isNotEmpty()) {
                analyzeAdapter.analyzeList = analyzeData
                analyzeAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "No analyze data found", Toast.LENGTH_SHORT).show()
            }
        }
        binding.containerProducts.setOnClickListener{
            val intent = Intent(requireContext(), ProductActivity::class.java)
            startActivity(intent)
        }
        binding.morningRoutineCardView.setOnClickListener {
            val intent = Intent(requireContext(), PagiHariActivity::class.java)
            startActivity(intent)
        }

        // Set OnClickListener for night routine icon
        binding.nightRoutineCardView.setOnClickListener {
            val intent = Intent(requireContext(), MalamHariActivity::class.java)
            startActivity(intent)
        }
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val intent = Intent(requireContext(), WelcomeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        return root
    }

    private fun setupRecyclerView() {
        analyzeAdapter = AnalyzeAdapter(emptyList()) { position ->
            // Menangani item click, dapatkan data berdasarkan posisi
            val analyze = analyzeAdapter.analyzeList[position]
            navigateToDetailAnalyzeActivity(analyze.id, position) // Pass position
        }
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticle.adapter = analyzeAdapter
    }

    private fun navigateToDetailAnalyzeActivity(analyzeId: Int, position: Int) {
        val intent = Intent(requireContext(), DetailAnalyzeActivity::class.java).apply {
            putExtra("ANALYZE_ID", analyzeId)
            putExtra("IMAGE_PATH", analyzeAdapter.analyzeList[position].imageName)
            putExtra("LEVEL", analyzeAdapter.analyzeList[position].level)
            putExtra("PREDICTION_RESULT", analyzeAdapter.analyzeList[position].predictionResult)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}