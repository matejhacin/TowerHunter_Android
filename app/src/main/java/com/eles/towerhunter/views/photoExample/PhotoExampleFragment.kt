package com.eles.towerhunter.views.photoExample

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentPhotoExampleBinding
import com.eles.towerhunter.helpers.extensions.requireAppCompatActivity
import com.eles.towerhunter.helpers.extensions.configureToolbar

class PhotoExampleFragment : Fragment() {

    private var _binding: FragmentPhotoExampleBinding? = null
    private val views get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoExampleBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar(true, true)
        initClickListener()
    }

    override fun onResume() {
        super.onResume()
        requireAppCompatActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    private fun initClickListener() {
        views.nextButton?.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_photoExampleFragment_to_cameraFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}