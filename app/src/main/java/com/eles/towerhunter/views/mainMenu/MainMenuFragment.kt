package com.eles.towerhunter.views.mainMenu

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
import com.eles.towerhunter.databinding.FragmentMainMenuBinding
import com.eles.towerhunter.views.activities.MainActivity

class MainMenuFragment : Fragment() {

    private val viewModel: MainMenuViewModel by viewModels()
    private var _binding: FragmentMainMenuBinding? = null
    private val views get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.show()
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initUi() {
        views.newPhotoButton.setOnClickListener { navigateToNewPhotoView() }
    }

    private fun navigateToNewPhotoView() {
        requireView().findNavController().navigate(R.id.action_mainMenuFragment_to_photoExampleFragment)
    }

}