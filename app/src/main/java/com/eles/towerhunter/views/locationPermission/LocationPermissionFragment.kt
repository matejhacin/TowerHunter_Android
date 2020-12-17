package com.eles.towerhunter.views.locationPermission

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentLocationPermissionBinding
import com.eles.towerhunter.views.activities.MainActivity

class LocationPermissionFragment : Fragment() {

    private val viewModel: LocationPermissionViewModel by viewModels()
    private var _binding: FragmentLocationPermissionBinding? = null
    private val views get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.prepareActivityResult(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationPermissionBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initData()
    }

    private fun initUi() {
        views.continueButton.setOnClickListener { viewModel.requestLocationPermission() }
    }

    private fun initData() {
        viewModel.isPermissionAccepted.observe(viewLifecycleOwner, Observer { isPermissionAccepted ->
            if (!isPermissionAccepted) {
                Toast.makeText(requireContext(), R.string.location_permission_warning, Toast.LENGTH_LONG).show()
            }
            navigateToMainView()
        })
    }

    private fun navigateToMainView() {
        viewModel.markOnboardingCompleted()
        startActivity(
            Intent(requireContext(), MainActivity::class.java)
        ).also { requireActivity().finish() }
    }

}