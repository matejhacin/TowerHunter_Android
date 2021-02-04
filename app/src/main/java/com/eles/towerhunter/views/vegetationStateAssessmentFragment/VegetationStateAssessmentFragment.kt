package com.eles.towerhunter.views.vegetationStateAssessmentFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.eles.towerhunter.R
import com.eles.towerhunter.data.VegetationState
import com.eles.towerhunter.databinding.FragmentVegetationStateAssessmentBinding
import com.eles.towerhunter.helpers.extensions.requireAppCompatActivity

class VegetationStateAssessmentFragment : Fragment() {

    private val viewModel: VegetationStateAssessmentViewModel by viewModels()
    private var _binding: FragmentVegetationStateAssessmentBinding? = null
    private val views get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVegetationStateAssessmentBinding.inflate(inflater, container, false)
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