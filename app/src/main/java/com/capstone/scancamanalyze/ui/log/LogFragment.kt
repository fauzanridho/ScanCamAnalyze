package com.capstone.scancamanalyze.ui.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scancamanalyze.SkinAdapter
import com.capstone.scancamanalyze.SkinItem
import com.capstone.scancamanalyze.databinding.FragmentLogBinding

class LogFragment : Fragment() {

    private var _binding: FragmentLogBinding? = null
    private val binding get() = _binding!!
    private lateinit var logAdapter: SkinAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val logViewModel =
            ViewModelProvider(this).get(LogViewModel::class.java)

        _binding = FragmentLogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setup RecyclerView
        logViewModel.logs.observe(viewLifecycleOwner) { logs ->
            logAdapter = SkinAdapter(logs) { selectedItem ->
                // Aksi ketika item diklik
                handleItemClick(selectedItem)
            }
            binding.recyclerViewLog.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = logAdapter
            }
        }

        return root
    }

    private fun handleItemClick(item: SkinItem) {
        // Contoh aksi: tampilkan pesan
        println("Item clicked: ${item.title}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
