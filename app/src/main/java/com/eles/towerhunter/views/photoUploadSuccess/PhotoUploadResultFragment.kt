package com.eles.towerhunter.views.photoUploadSuccess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentPhotoUploadResultBinding
import com.eles.towerhunter.helpers.extensions.requireAppCompatActivity
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
    }

    override fun onResume() {
        super.onResume()
        requireAppCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initUi() {
        views.newPhotoButton.setOnClickListener { navigateToCameraView() }

        val success = args.uploadSuccess
        imageView.setImageResource(if (success) R.drawable.img_photo_upload_success else R.drawable.img_photo_upload_fail)
        messageTextView.text = if (success) getString(R.string.photo_upload_result_success) else getString(R.string.photo_upload_result_fail)
    }

    private fun navigateToCameraView() {
        requireView().findNavController().navigate(R.id.action_photoUploadSuccessFragment_to_cameraFragment)
    }

}