package com.global.loveto.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.global.loveto.R
import com.global.loveto.domain.model.Farmer
import com.global.loveto.platform.BaseActivityFragment
import com.global.loveto.ui.fragment.AgreementSubmittedFragment

class AgreementSubmittedActivity: BaseActivityFragment() {

    private var synced : Boolean = false
    lateinit var farmer : Farmer

    companion object {

        private const val synced_tag = "synced_tag"
        private const val farmer_tag = "farmer_tag"

        fun callingIntent(context: Context?, farmer : Farmer, synced : Boolean): Intent {
            val intent = Intent(context, AgreementSubmittedActivity::class.java)
            val bundle = Bundle()
            bundle.putBoolean(synced_tag, synced)
            bundle.putSerializable(farmer_tag, farmer)
            intent.putExtras(bundle)

            return intent

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        synced = intent.extras?.getBoolean(synced_tag) ?: false
        farmer = intent.extras?.getSerializable(farmer_tag) as Farmer
        super.onCreate(savedInstanceState)

    }

    override fun layoutId() = R.layout.activity_layout_no_toolbar

    override fun fragment() = AgreementSubmittedFragment.newInstance(farmer,synced)

}