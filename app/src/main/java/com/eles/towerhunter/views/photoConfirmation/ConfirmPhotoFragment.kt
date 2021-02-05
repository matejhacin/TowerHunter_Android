package com.eles.towerhunter.views.photoConfirmation

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentConfirmPhotoBinding
import com.eles.towerhunter.helpers.extensions.configureToolbar

class ConfirmPhotoFragment : Fragment() {

    private val viewModel: ConfirmPhotoViewModel by viewModels()
    private var _binding: FragmentConfirmPhotoBinding? = null
    private val views get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentConfirmPhotoBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar(true, true)
        initUi()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun initUi() {
        views.imageView.setImageURI(viewModel.photoUri)
        views.confirmButton.setOnClickListener { navigateForward() }
        views.retakeButton.setOnClickListener {
            viewModel.retakePhotoClicked()
            requireActivity().onBackPressed()
        }
    }

    private fun navigateForward() {
        if (viewModel.isUserQualifiedToAssessState) {
            requireView().findNavController().navigate(R.id.action_confirmPhotoFragment_to_vegetationStateQuestionFragment)
        } else {
            requireView().findNavController().navigate(R.id.action_confirmPhotoFragment_to_photoUploadFragment)
        }
    }

}