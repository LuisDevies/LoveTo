package com.global.loveto.ui.fragment

import android.net.ConnectivityManager
import android.os.Bundle

import com.global.loveto.R
import com.global.loveto.core.extension.observe
import com.global.loveto.core.coroutines.Result
import com.global.loveto.core.enums.Operation
import com.global.loveto.core.extension.isNetworkAvailable
import com.global.loveto.core.extension.isWifiAvailable
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.domain.model.Claim
import com.global.loveto.domain.model.Farmer
import com.global.loveto.domain.model.toOperationEntity
import com.global.loveto.platform.BaseFragment
import com.global.loveto.preferences.SharedPreferencesHelper
import com.global.loveto.presentation.ClaimViewModel
import kotlinx.android.synthetic.main.fragment_claims.*
import kotlinx.android.synthetic.main.toolbar_back.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClaimFragment : BaseFragment() {

    private lateinit var farmer: Farmer
    private var options: MutableList<String> = mutableListOf()
    private var claimToSave: Claim? = null
    private var isEditing: Boolean = false

    private val claimViewModel: ClaimViewModel by viewModel()
    private val connectivityManager: ConnectivityManager by inject()

    companion object {

        private const val farmer_tag = "farmer_tag"

        fun newInstance(farmer: Farmer): ClaimFragment {
            val fragment = ClaimFragment()
            val arguments = Bundle()
            arguments.putSerializable(farmer_tag, farmer)
            fragment.arguments = arguments

            return fragment
        }
    }

    override fun layoutId(): Int = R.layout.fragment_claims

    override fun initializeView() {
        farmer = arguments?.getSerializable(farmer_tag) as Farmer
        bt_soil.tag = false
        bt_catchment_managment.tag = false
        bt_water.tag = false
        bt_land_use.tag = false
        bt_crops.tag = false
        bt_animals.tag = false

        with(claimViewModel) {
            observe(claim, ::handleLocalSave)
            observe(any, ::handleRemoteSave)
        }

    }

    private fun handleLocalSave(result: Result<Claim>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                hideLoading()
                if (connectivityManager.isNetworkAvailable() && connectivityManager.isWifiAvailable() && !isEditing) {
                    claimToSave = result.value
                    claimViewModel.saveRemoteClaim(result.value)
                } else {
                    if (!isEditing) {
                        navigator.goToClaimSubmitted(context, farmer, false)
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
                claimToSave?.synced = true
                isEditing = true
                claimViewModel.saveLocalClaim(claimToSave!!.toOperationEntity())
                navigator.goToClaimSubmitted(context, farmer, true)
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

    override fun initializeListeners() {
        toolbar_back_arrow.setOnClickListener {
            navigator.goToHome(context)
        }

        bt_soil.setOnClickListener {

            bt_soil.performLongClick()
            bt_soil.tag = !(bt_soil.tag as Boolean)
            if (bt_soil.tag as Boolean) {
                bt_soil.background = resources.getDrawable(R.drawable.bg_green_border_white_rounded)
                bt_soil.setTextColor(resources.getColor(R.color.colorAccent))
                options.add("1")
            } else {
                bt_soil.background = resources.getDrawable(R.drawable.bg_grey_rounded)
                bt_soil.setTextColor(resources.getColor(R.color.color_text_title))
                options.remove("1")
            }

        }
        bt_catchment_managment.setOnClickListener {

            bt_catchment_managment.performLongClick()
            bt_catchment_managment.tag = !(bt_catchment_managment.tag as Boolean)
            if (bt_catchment_managment.tag as Boolean) {
                bt_catchment_managment.background =
                    resources.getDrawable(R.drawable.bg_green_border_white_rounded)
                bt_catchment_managment.setTextColor(resources.getColor(R.color.colorAccent))
                options.add("2")
            } else {
                bt_catchment_managment.background =
                    resources.getDrawable(R.drawable.bg_grey_rounded)
                bt_catchment_managment.setTextColor(resources.getColor(R.color.color_text_title))
                options.remove("2")
            }
        }
        bt_water.setOnClickListener {

            bt_water.performLongClick()
            bt_water.tag = !(bt_water.tag as Boolean)
            if (bt_water.tag as Boolean) {
                bt_water.background =
                    resources.getDrawable(R.drawable.bg_green_border_white_rounded)
                bt_water.setTextColor(resources.getColor(R.color.colorAccent))
                options.add("3")
            } else {
                bt_water.background = resources.getDrawable(R.drawable.bg_grey_rounded)
                bt_water.setTextColor(resources.getColor(R.color.color_text_title))
                options.remove("3")
            }
        }
        bt_land_use.setOnClickListener {

            bt_land_use.performLongClick()
            bt_land_use.tag = !(bt_land_use.tag as Boolean)
            if (bt_land_use.tag as Boolean) {
                bt_land_use.background =
                    resources.getDrawable(R.drawable.bg_green_border_white_rounded)
                bt_land_use.setTextColor(resources.getColor(R.color.colorAccent))
                options.add("4")
            } else {
                bt_land_use.background = resources.getDrawable(R.drawable.bg_grey_rounded)
                bt_land_use.setTextColor(resources.getColor(R.color.color_text_title))
                options.remove("4")
            }
        }
        bt_crops.setOnClickListener {

            bt_crops.performLongClick()
            bt_crops.tag = !(bt_crops.tag as Boolean)
            if (bt_crops.tag as Boolean) {
                bt_crops.background =
                    resources.getDrawable(R.drawable.bg_green_border_white_rounded)
                bt_crops.setTextColor(resources.getColor(R.color.colorAccent))
                options.add("5")
            } else {
                bt_crops.background = resources.getDrawable(R.drawable.bg_grey_rounded)
                bt_crops.setTextColor(resources.getColor(R.color.color_text_title))
                options.remove("5")
            }

        }
        bt_animals.setOnClickListener {

            bt_animals.performLongClick()
            bt_animals.tag = !(bt_animals.tag as Boolean)
            if (bt_animals.tag as Boolean) {
                bt_animals.background =
                    resources.getDrawable(R.drawable.bg_green_border_white_rounded)
                bt_animals.setTextColor(resources.getColor(R.color.colorAccent))
                options.add("6")
            } else {
                bt_animals.background = resources.getDrawable(R.drawable.bg_grey_rounded)
                bt_animals.setTextColor(resources.getColor(R.color.color_text_title))
                options.remove("6")
            }
        }

        bt_submit.setOnClickListener {

            if (options.size > 0) {
                val claim = OperationEntity(
                    walkerId = SharedPreferencesHelper.userId!!,
                    farmerNumber = farmer.number,
                    options = options,
                    synced = false,
                    operation = Operation.CLAIM,
                    video = ""
                )
                claimViewModel.saveLocalClaim(claim)
            } else {
                notify(R.string.please_select_at_least_one_option)
            }
        }

    }

}
