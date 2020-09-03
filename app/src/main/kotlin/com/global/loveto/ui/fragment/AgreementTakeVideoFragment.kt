package com.global.loveto.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import com.global.loveto.core.coroutines.Result
import android.view.View
import android.widget.MediaController

import com.global.loveto.R
import com.global.loveto.core.enums.Operation
import com.global.loveto.core.extension.isNetworkAvailable
import com.global.loveto.core.extension.isWifiAvailable
import com.global.loveto.core.extension.observe
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.domain.model.Agreement
import com.global.loveto.domain.model.Farmer
import com.global.loveto.domain.model.toOperationEntity
import com.global.loveto.navigation.record_video
import com.global.loveto.platform.BaseFragment
import com.global.loveto.preferences.SharedPreferencesHelper
import com.global.loveto.presentation.AgreementViewModel
import kotlinx.android.synthetic.main.fragment_agreement_take_video.*
import kotlinx.android.synthetic.main.toolbar_back.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class AgreementTakeVideoFragment : BaseFragment() {

    private lateinit var farmer: Farmer
    private var agreement: Agreement? = null
    private var agreementEntity: OperationEntity? = null
    private var isEditing: Boolean = false

    private val agreementViewModel: AgreementViewModel by viewModel()
    private val connectivityManager: ConnectivityManager by inject()

    companion object {

        private const val farmer_tag = "farmer_tag"

        fun newInstance(farmer: Farmer): AgreementTakeVideoFragment {
            val fragment = AgreementTakeVideoFragment()
            val arguments = Bundle()
            arguments.putSerializable(farmer_tag, farmer)
            fragment.arguments = arguments

            return fragment
        }
    }

    override fun layoutId() = R.layout.fragment_agreement_take_video

    override fun initializeView() {
        farmer = arguments?.getSerializable(farmer_tag) as Farmer

        with(agreementViewModel) {
            observe(agreement, ::handleLocalSave)
            observe(any, ::handleRemoteSave)
        }
    }

    override fun initializeListeners() {
        toolbar_back_arrow.setOnClickListener { navigator.goToHome(context) }
        bt_take_video.setOnClickListener {
            if (bt_take_video.text == getString(R.string.take_video)) {
                if (activity != null) {
                    requestStoragePermission(activity as Activity)
                }
            } else {
                saveAgreement()
            }
        }
        bt_retake.setOnClickListener {
            if (activity != null) {
                requestStoragePermission(activity as Activity)
            }
        }
    }

    override fun permissionRequestResult(granted: Boolean, permissionTag: String) {
        when (permissionTag) {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                permissionVideo(granted)
            }
        }
    }

    private fun permissionVideo(permissionGranted: Boolean) {
        if (permissionGranted) {
            recordVideo()
        } else {
            notify(R.string.permissions_error)
        }
    }

    private fun recordVideo() {
        showLoading()
        createVideoFile()
        if (imageVideoFile != null) {
            navigator.recordVideo(this, record_video, imageVideoFile!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                record_video -> {
                    /*val thumbnail = ThumbnailUtils.createVideoThumbnail(imageVideoFile?.path,
                        MediaStore.Images.Thumbnails.MINI_KIND)
                    val thumbUri = thumbnail.saveToInternalStorageNameVideoToImage(imageVideoFile?.name, context)
                    sendMedia(Uri.parse(imageVideoFile?.path), thumbUri)*/
                    cl_agreement_data.visibility = View.INVISIBLE
                    vv_agreement.visibility = View.VISIBLE
                    bt_retake.visibility = View.VISIBLE
                    bt_take_video.text = getString(R.string.done)
                    hideLoading()
                    vv_agreement.setVideoPath(imageVideoFile?.path)
                    vv_agreement.setMediaController(MediaController(context))
                    vv_agreement.requestFocus()
                    vv_agreement.start()
                    agreementEntity = OperationEntity(
                        walkerId = SharedPreferencesHelper.userId ?: "",
                        farmerNumber = farmer.number,
                        options = emptyList(),
                        video = convertImageFileToBase64(imageVideoFile!!),
                        synced = false,
                        operation = Operation.AGREEMENT
                    )

                }
            }
        } else {
            hideLoading()
        }
    }

    private fun saveAgreement() {
        showLoading()
        agreementViewModel.saveLocalAgreement(agreementEntity!!)
    }

    private fun handleLocalSave(result: Result<Agreement>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                hideLoading()
                if (connectivityManager.isNetworkAvailable() && connectivityManager.isWifiAvailable() && !isEditing) {
                    agreement = result.value
                    agreementViewModel.saveRemoteAgreement(result.value)
                } else {
                    if (!isEditing) {
                        navigator.goToAgreementSubmitted(context, farmer, false)
                    }
                }
            }
            is Result.OnError -> {
                hideLoading()
                notify(R.string.error)
            }
            else -> {
                hideLoading()
                notify(R.string.error)
            }
        }
    }

    private fun handleRemoteSave(result: Result<Any>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                hideLoading()
                agreement!!.synced = true
                isEditing = true
                agreementViewModel.saveLocalAgreement(agreement!!.toOperationEntity())
                navigator.goToAgreementSubmitted(context, farmer, true)
            }
            is Result.OnError -> {
                hideLoading()
                notify(R.string.error)
            }
            else -> {
                hideLoading()
                notify(R.string.error)
            }
        }
    }


}