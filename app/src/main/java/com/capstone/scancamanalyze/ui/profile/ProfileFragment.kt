package com.capstone.scancamanalyze.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.databinding.FragmentProfileBinding
import com.capstone.scancamanalyze.ui.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Logout
        binding.menuLogout.setOnClickListener {
            viewModel.logout()
        }

        // Observe user session
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val intent = Intent(requireContext(), WelcomeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        // Dark mode toggle
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                viewModel.setDarkMode(isChecked)
            }
        }

        // Observe dark mode setting
        lifecycleScope.launch {
            viewModel.isDarkMode().observe(viewLifecycleOwner) { isDarkMode ->
                binding.switchDarkMode.isChecked = isDarkMode
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
