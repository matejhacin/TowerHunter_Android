package com.eles.towerhunter.views.camera

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentCameraBinding
import com.eles.towerhunter.views.activities.MainActivity

class CameraFragment : Fragment() {

    private val viewModel: CameraViewModel by viewModels()
    private var _binding: FragmentCameraBinding? = null
    private val views get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.prepareActivityResult(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        initData()
        viewModel.initCameraWithPermissionCheck(this, views.viewFinder.surfaceProvider)
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.hide()
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    /*
    Data
     */

    private fun initData() {
        // Permission change event
        viewModel.cameraPermissionGranted.observe(viewLifecycleOwner, Observer { granted ->
            if (granted) {
                viewModel.initCamera(this, views.viewFinder.surfaceProvider)
            } else {
                Toast.makeText(requireContext(), R.string.camera_missing_permission_warning, Toast.LENGTH_LONG).show()
            }
        })

        // Photo capture event
        viewModel.photoCaptured.observe(viewLifecycleOwner, Observer {
            navigateToPhotoConfirmation()
        })

        // Photo capture error event
        viewModel.photoCaptureError.observe(viewLifecycleOwner, Observer {
            showError(it.localizedMessage ?: getString(R.string.camera_unknown_error))
        })
    }

    /*
    UI
     */

    private fun initClickListener() {
        views.cameraCaptureButton.setOnClickListener {
            viewModel.takePhoto(requireActivity())
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToPhotoConfirmation() {
        requireView().findNavController().navigate(R.id.action_cameraFragment_to_confirmPhotoFragment)
    }

}