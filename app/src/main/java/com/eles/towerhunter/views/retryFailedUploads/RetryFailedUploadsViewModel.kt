package com.eles.towerhunter.views.retryFailedUploads

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.helpers.SingleLiveEvent
import com.eles.towerhunter.network.clients.ImageUploadClient

class RetryFailedUploadsViewModel(
    private val storage: LocalStorage = LocalStorage,
    private val uploadClient: ImageUploadClient = ImageUploadClient()
) : ViewModel() {

    private val failedUploads = storage.failedUploads.toMutableList()
    val failedUploadsCount = failedUploads.count()

    private val _uploadedCount = SingleLiveEvent<Int>()
    val uploadedCount: LiveData<Int>
        get() = _uploadedCount

    private val _error = SingleLiveEvent<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    init {
        _uploadedCount.value = 0
    }

    fun beginUploading() {
        if (failedUploads.isNotEmpty()) {
            uploadNext()
        }
    }

    private fun uploadNext() {
        val image = failedUploads.firstOrNull()

        if (image != null) {
            uploadClient.createImage(image) { success ->
                if (success) {
                    _uploadedCount.value = _uploadedCount.value!! + 1
                    failedUploads.remove(image)
                    uploadNext()
                } else {
                    _error.value = true
                }
            }
        } else {
            finishUploading()
        }
    }

    private fun finishUploading() {
        storage.failedUploads = failedUploads
    }

}