package com.capstone.scancamanalyze.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.scancamanalyze.R
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
        binding.containerLogout.setOnClickListener {
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
        binding.containerLanguage.setOnClickListener {
            val languages = arrayOf("English", "Bahasa Indonesia")
            val languageCodes = arrayOf("en", "id")
            var selectedLanguageIndex = 0

            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.language))
                .setSingleChoiceItems(languages, selectedLanguageIndex) { _, which ->
                    selectedLanguageIndex = which
                }
                .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                    val selectedLanguageCode = languageCodes[selectedLanguageIndex]
                    viewModel.changeLanguage(requireContext(), selectedLanguageCode)
                    // Restart activity to apply changes
                    val intent = requireActivity().intent
                    requireActivity().finish()
                    startActivity(intent)
                }
                .setNegativeButton(getString(android.R.string.cancel), null)
                .show()
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
