package com.global.loveto.ui.fragment

import com.global.loveto.R
import com.global.loveto.core.enums.Operation
import com.global.loveto.platform.BaseFragment
import com.global.loveto.preferences.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {

    companion object {

        fun newInstance() = HomeFragment()

    }

    override fun layoutId() = R.layout.fragment_home

    override fun initializeView() {

    }

    override fun initializeListeners() {

        tv_agreement.setOnClickListener { navigator.goToSelectFarmer(context,Operation.AGREEMENT) }
        tv_claim.setOnClickListener { navigator.goToSelectFarmer(context,Operation.CLAIM) }
        tv_syncs.setOnClickListener { navigator.goToSync(context) }
        tv_logout.setOnClickListener { logout() }
    }

    private fun logout () {
        SharedPreferencesHelper.cleanSession()
        navigator.goToLogin(context)
    }


}
