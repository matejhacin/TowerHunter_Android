package com.eles.towerhunter.views.photoUploadSuccess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentPhotoUploadResultBinding
import com.eles.towerhunter.helpers.extensions.configureToolbar
import kotlinx.android.synthetic.main.fragment_photo_upload_result.*

class PhotoUploadResultFragment : Fragment() {

    private val viewModel: PhotoUploadResultViewModel by viewModels()
    private var _binding: FragmentPhotoUploadResultBinding? = null
    private val views get() = _binding!!
    private val args: PhotoUploadResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoUploadResultBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initClickListeners()
    }

    private fun initUi() {
        configureToolbar(true, true, R.drawable.ic_close_white)

        val success = args.uploadSuccess
        imageView.setImageResource(if (success) R.drawable.img_photo_upload_success else R.drawable.img_photo_upload_fail)
        messageTextView.text = if (success) getString(R.string.photo_upload_result_success) else getString(R.string.photo_upload_result_fail)
    }

    private fun initClickListeners() {
        views.newPhotoButton.setOnClickListener { navigateToCameraView() }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateToMainMenu()
        }
    }

    private fun navigateToCameraView() {
        requireView().findNavController().navigate(R.id.action_photoUploadResultFragment_to_cameraFragment)
    }

    private fun navigateToMainMenu() {
        requireView().findNavController().navigate(R.id.action_photoUploadResultFragment_to_mainMenuFragment)
    }

}