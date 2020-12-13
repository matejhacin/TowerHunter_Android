package com.eles.towerhunter.views.newUser

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentNewUserBinding

class NewUserFragment : Fragment(), View.OnClickListener {

    private val viewModel: NewUserViewModel by viewModels()
    private var _binding: FragmentNewUserBinding? = null
    private val views: FragmentNewUserBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewUserBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initViews() {
        views.yesButton.setOnClickListener(this)
        views.noButton.setOnClickListener(this)
    }

    private fun navigateNext() {
        requireView().findNavController().navigate(R.id.action_newUserFragment_to_photoExampleFragment)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            views.yesButton.id -> viewModel.updateUserQualificationConfiguration(true)
            views.noButton.id -> viewModel.updateUserQualificationConfiguration(false)
        }
        navigateNext()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}