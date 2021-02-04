package com.eles.towerhunter.views.vegetationStateQuestionFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.eles.towerhunter.R
import com.eles.towerhunter.data.VegetationState
import com.eles.towerhunter.databinding.FragmentVegetationStateQuestionBinding
import com.eles.towerhunter.helpers.extensions.requireAppCompatActivity
import kotlinx.android.synthetic.main.fragment_vegetation_state_question.*

class VegetationStateQuestionFragment : Fragment() {

    private val viewModel: VegetationStateQuestionViewModel by viewModels()
    private var _binding: FragmentVegetationStateQuestionBinding? = null
    private val views get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVegetationStateQuestionBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onResume() {
        super.onResume()
        requireAppCompatActivity().supportActionBar?.show()
        requireAppCompatActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initUi() {
        views.notQualifiedButton.setOnClickListener {
            stateSelected(VegetationState.unknown)
        }
        views.maybeButton.setOnClickListener {
            stateSelected(VegetationState.maintenanceMaybeNeeded)
        }
        views.yesButton.setOnClickListener {
            stateSelected(VegetationState.maintenanceNeeded)
        }
        views.noButton.setOnClickListener {
            stateSelected(VegetationState.maintenanceNotNeeded)
        }
    }

    private fun stateSelected(state: VegetationState) {
        viewModel.saveState(state)
        navigateToPhotoUpload()
    }

    private fun navigateToPhotoUpload() {
        requireView().findNavController().navigate(R.id.action_vegetationStateQuestionFragment_to_photoUploadFragment)
    }

}