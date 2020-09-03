package com.global.loveto.ui.fragment



import android.os.Bundle
import android.view.View
import com.global.loveto.R
import com.global.loveto.domain.model.Farmer
import com.global.loveto.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_claim_submitted_fargment.*
import kotlinx.android.synthetic.main.toolbar_back.*


class AgreementSubmittedFragment : BaseFragment() {

    private var synced: Boolean = false
    lateinit var farmer: Farmer

    companion object {
        private const val synced_tag = "synced_tag"
        private const val farmer_tag = "farmer_tag"

        fun newInstance(farmer: Farmer, synced: Boolean): AgreementSubmittedFragment {
            val fragment = AgreementSubmittedFragment()
            val arguments = Bundle()
            arguments.putBoolean(synced_tag, synced)
            arguments.putSerializable(farmer_tag, farmer)
            fragment.arguments = arguments

            return fragment
        }
    }

    override fun layoutId() = R.layout.fragment_agreement_submitted

    override fun initializeView() {
        synced = arguments?.getBoolean(synced_tag) ?: false
        farmer = arguments?.getSerializable(farmer_tag) as Farmer

        tv_claim_submitted.text = getString(R.string.claim_submitted_text, farmer.name)

        tv_sync_on_wifi.visibility = if (synced) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }

    }

    override fun initializeListeners() {
        toolbar_back_arrow.setOnClickListener {
            navigator.goToHome(context)
        }

        bt_done.setOnClickListener {
            navigator.goToHome(context)
        }

    }

    override fun onBackPressed() {
        navigator.goToHome(context)
    }


}
