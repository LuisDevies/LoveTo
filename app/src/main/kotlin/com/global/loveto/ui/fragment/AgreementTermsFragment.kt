package com.global.loveto.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.global.loveto.R
import com.global.loveto.domain.model.Farmer
import com.global.loveto.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_agreement_terms.*
import kotlinx.android.synthetic.main.toolbar_back.*


class AgreementTermsFragment : BaseFragment() {

    private lateinit var farmer: Farmer

    companion object {

        private const val farmer_tag = "farmer_tag"

        fun newInstance(farmer: Farmer): AgreementTermsFragment {
            val fragment = AgreementTermsFragment()
            val arguments = Bundle()
            arguments.putSerializable(farmer_tag, farmer)
            fragment.arguments = arguments

            return fragment
        }
    }

    override fun layoutId() = R.layout.fragment_agreement_terms

    override fun initializeView() {
        farmer = arguments?.getSerializable(farmer_tag) as Farmer
    }

    override fun initializeListeners() {
        toolbar_back_arrow.setOnClickListener { navigator.goToHome(context) }
        bt_confirm.setOnClickListener { navigator.goToAgreementTakeVideo(context,farmer) }
    }

}
