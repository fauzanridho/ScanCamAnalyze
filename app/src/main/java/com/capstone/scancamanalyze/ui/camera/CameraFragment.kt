package com.capstone.scancamanalyze.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.scancamanalyze.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cameraViewModel =
            ViewModelProvider(this).get(CameraViewModel::class.java)

        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.resultText
        cameraViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

//    private val  akmal : String = "akmal"
//    https://github.com/fauzanridho/ScanCamAnalyze.git
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}