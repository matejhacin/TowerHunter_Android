package com.eles.towerhunter.views.photoUploadSuccess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentPhotoUploadSuccessBinding

class PhotoUploadSuccessFragment : Fragment() {

    private val viewModel: PhotoUploadSuccessViewModel by viewModels()
    private var _binding: FragmentPhotoUploadSuccessBinding? = null
    private val views get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoUploadSuccessBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        views.newPhotoButton.setOnClickListener { navigateToCameraView() }
    }

    private fun navigateToCameraView() {
        requireView().findNavController().navigate(R.id.action_photoUploadSuccessFragment_to_cameraFragment)
    }

}