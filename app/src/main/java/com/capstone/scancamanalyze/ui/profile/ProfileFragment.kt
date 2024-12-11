package com.capstone.scancamanalyze.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.adapter.AvatarAdapter
import com.capstone.scancamanalyze.data.avatars
import com.capstone.scancamanalyze.databinding.FragmentProfileBinding
import com.capstone.scancamanalyze.ui.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var avatarAdapter: AvatarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Load the saved avatar from SharedPreferences
        val sharedPref =
            requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val avatarUrl = sharedPref.getString("avatar_url", null)
        avatarUrl?.let {
            Glide.with(this).load(it).into(binding.imgAvatar)
        }

        // Logout
        binding.containerLogout.setOnClickListener {
            viewModel.logout()
        }

        binding.imgAvatar.setOnClickListener {
            if (binding.rvAvatars.visibility == View.GONE) {
                binding.rvAvatars.apply {
                    visibility = View.VISIBLE
                    animate().alpha(1f).setDuration(300).start()
                }
                binding.containerDarkMode.visibility = View.GONE
                binding.containerLanguage.visibility = View.GONE
                binding.containerLogout.visibility = View.GONE
                binding.tvSetting.visibility = View.GONE
                binding.tvName.visibility = View.GONE
            } else {
                binding.rvAvatars.animate().alpha(0f).setDuration(300).withEndAction {
                    binding.rvAvatars.visibility = View.GONE
                }.start()
                binding.containerDarkMode.visibility = View.VISIBLE
                binding.containerLanguage.visibility = View.VISIBLE
                binding.containerLogout.visibility = View.VISIBLE
                binding.tvSetting.visibility = View.VISIBLE
                binding.tvName.visibility = View.VISIBLE
            }
        }

        // Initialize Avatar Adapter
        avatarAdapter = AvatarAdapter(avatars) { selectedImage ->
            Glide.with(this).load(selectedImage).into(binding.imgAvatar)

            // Save the selected avatar to SharedPreferences
            val editor = sharedPref.edit()
            editor.putString("avatar_url", selectedImage)
            editor.apply()

            binding.rvAvatars.visibility = View.GONE
            binding.containerDarkMode.visibility = View.VISIBLE
            binding.containerLanguage.visibility = View.VISIBLE
            binding.containerLogout.visibility = View.VISIBLE
            binding.tvSetting.visibility = View.VISIBLE
            binding.tvName.visibility = View.VISIBLE
        }
        binding.rvAvatars.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.rvAvatars.adapter = avatarAdapter

        // Observe user session
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            val name = user.email
            binding.tvName.text = name
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
