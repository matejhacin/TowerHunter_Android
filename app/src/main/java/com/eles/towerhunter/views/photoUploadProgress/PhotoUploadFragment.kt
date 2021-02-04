package com.eles.towerhunter.views.photoUploadProgress

import android.content.pm.ActivityInfo
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.eles.towerhunter.R
import com.eles.towerhunter.helpers.extensions.requireAppCompatActivity
import com.eles.towerhunter.views.photoUploadSuccess.PhotoUploadResultFragmentArgs

class PhotoUploadFragment : Fragment() {

    private val viewModel: PhotoUploadViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.photo_upload_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        viewModel.beginUpload()
    }

    override fun onResume() {
        super.onResume()
        requireAppCompatActivity().supportActionBar?.show()
        requireAppCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(false)
        requireAppCompatActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initData() {
        viewModel.didUploadData.observe(viewLifecycleOwner) { success ->
            navigateToUploadResult(success)
        }
    }

    private fun navigateToUploadResult(success: Boolean) {
        val directions = PhotoUploadFragmentDirections.actionPhotoUploadFragmentToPhotoUploadResultFragment(success)
        requireView().findNavController().navigate(directions)
    }

}