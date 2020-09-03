package com.global.loveto.navigation

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.global.loveto.BuildConfig
import com.global.loveto.core.enums.Operation
import com.global.loveto.core.extension.startActivityBackStack
import com.global.loveto.core.extension.startActivityNoBackStack
import com.global.loveto.domain.model.Farmer
import com.global.loveto.platform.BaseFragment
import com.global.loveto.preferences.SharedPreferencesHelper
import com.global.loveto.ui.activity.*
import java.io.File

const val record_video = 1

class Navigator() {


    fun showMain(context: Context?, logged: Boolean = false, user: String = "") {
        val userID: String = if (user.isNotEmpty()) user else SharedPreferencesHelper.userId ?: ""


        if (userID.isNotEmpty()) {
            goToHome(context)
        } else {
            goToLogin(context)
        }
        /* logged -> showDashboard(context, userID, tab)
         authenticator.userLoggedIn() -> showDashboard(context, userID, tab, chatId)
         else -> showSplash(context)*/
    }

    fun goToLogin(context: Context?) =
        context?.startActivityNoBackStack(LoginActivity.callingIntent(context))

    fun goToHome(context: Context?) =
        context?.startActivityNoBackStack(HomeActivity.callingIntent(context))

    fun goToSelectFarmer(context: Context?, operation: Operation) =
        context?.startActivityBackStack(SelectFarmerActivity.callingIntent(context, operation))

    fun goToFarmerDetail(context: Context?, operation: Operation, farmer: Farmer) =
        context?.startActivityBackStack(
            FarmerDetailActivity.callingIntent(
                context,
                operation,
                farmer
            )
        )

    fun goToClaim(context: Context?, farmer: Farmer) = context?.startActivityBackStack(
        ClaimActivity.callingIntent(context, farmer)
    )

    fun goToClaimSubmitted(context: Context?, farmer: Farmer, synced : Boolean) = context?.startActivityNoBackStack(
        ClaimSubmittedActivity.callingIntent(context, farmer,synced)
    )

    fun goToAgreementSubmitted(context: Context?, farmer: Farmer, synced : Boolean) = context?.startActivityNoBackStack(
        AgreementSubmittedActivity.callingIntent(context, farmer,synced)
    )

    fun goToAgreement(context: Context?, farmer: Farmer) = context?.startActivityBackStack(
        AgreementTermsActivity.callingIntent(context, farmer)
    )

    fun goToAgreementTakeVideo(context: Context?, farmer: Farmer) = context?.startActivityBackStack(
        AgreementTakeVideoActivity.callingIntent(context, farmer)
    )

    fun goToSync(context: Context?) =
        context?.startActivityBackStack(SyncActivity.callingIntent(context))

    fun recordVideo(fragment: Fragment?, tag: Int, imageFile: File) {
        val recordVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)

        recordVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.2) // 0.5 medium quality
        recordVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 600) //600 seconds
        recordVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 100 * 1024 * 1024) //100*1024*1024=100MiB


        if (recordVideoIntent.resolveActivity(fragment?.activity?.packageManager) != null) {
            val photoURI = FileProvider.getUriForFile(fragment?.activity?.applicationContext!!,
                "${BuildConfig.APPLICATION_ID}.fileprovider",
                imageFile)
            recordVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            fragment.startActivityForResult(recordVideoIntent, tag)
        }
    }


    fun goBack(activity: FragmentActivity?) {
        val appFragments =
            activity?.supportFragmentManager?.fragments?.filter { it is BaseFragment }
        if (appFragments?.size?.compareTo(1) ?: 0 > 0) {
            activity?.supportFragmentManager?.popBackStack()
        } else {
            activity?.finish()
        }
    }
}