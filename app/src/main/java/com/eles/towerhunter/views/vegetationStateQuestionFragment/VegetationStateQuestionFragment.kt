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
import com.eles.towerhunter.views.activities.MainActivity

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
        initData()
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.show()
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initUi() {
        views.notQualifiedButton.setOnClickListener { viewModel.uploadData(VegetationState.UNKNOWN) }
        views.maybeButton.setOnClickListener { viewModel.uploadData(VegetationState.MAINTENANCE_MAYBE_NEEDED) }
        views.yesButton.setOnClickListener { viewModel.uploadData(VegetationState.MAINTENANCE_NEEDED) }
        views.noButton.setOnClickListener { viewModel.uploadData(VegetationState.MAINTENANCE_NOT_NEEDED) }
    }

    private fun initData() {
        viewModel.didUploadData.observe(viewLifecycleOwner, Observer<Boolean> { didUpload ->
            if (didUpload) {
                navigateToUploadSuccessView()
            }
        })
    }

    private fun navigateToUploadSuccessView() {
        requireView().findNavController().navigate(R.id.action_vegetationStateQuestionFragment_to_photoUploadSuccessFragment)
    }

}