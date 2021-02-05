package com.eles.towerhunter.views.retryFailedUploads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.RetryFailedUploadsFragmentBinding
import com.eles.towerhunter.helpers.extensions.configureToolbar

class RetryFailedUploadsFragment : Fragment() {

    private val viewModel: RetryFailedUploadsViewModel by viewModels()
    private lateinit var views: RetryFailedUploadsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        views = RetryFailedUploadsFragmentBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.beginUploading()
        initData()
        initUi()
    }

    private fun initUi() {
        configureToolbar(true, true)
        views.finishButton.isVisible = false
        views.finishButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initData() {
        viewModel.uploadedCount.observe(viewLifecycleOwner) {
            updateUploadCount()
        }
        viewModel.uploadFinished.observe(viewLifecycleOwner) {
            uploadFinished()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }
    }

    private fun updateUploadCount() {
        val uploadedCount = viewModel.uploadedCount.value
        val totalCount = viewModel.failedUploadsCount
        views.textView.text = "${getString(R.string.retry_failed_uploads_progress)}\n$uploadedCount/$totalCount"
    }

    private fun uploadFinished() {
        views.progressBar.isVisible = false
        views.finishButton.isVisible = true
    }

    private fun showError() {
        views.textView.text = getString(R.string.retry_failed_uploads_error)
        views.finishButton.isVisible = true
        views.progressBar.isVisible = false
    }

}