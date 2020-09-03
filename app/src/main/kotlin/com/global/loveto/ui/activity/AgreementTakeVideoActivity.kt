package com.global.loveto.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.global.loveto.R
import com.global.loveto.domain.model.Farmer
import com.global.loveto.platform.BaseActivityFragment
import com.global.loveto.ui.fragment.AgreementTakeVideoFragment

class AgreementTakeVideoActivity : BaseActivityFragment() {
    lateinit var farmer: Farmer

    companion object {

        private const val farmer_tag = "farmer_tag"

        fun callingIntent(context: Context?, farmer: Farmer): Intent {
            val intent = Intent(context, AgreementTakeVideoActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(farmer_tag, farmer)
            intent.putExtras(bundle)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        farmer = intent.extras?.getSerializable(farmer_tag) as Farmer
        super.onCreate(savedInstanceState)

    }

    override fun layoutId() = R.layout.activity_layout_back

    override fun fragment() = AgreementTakeVideoFragment.newInstance(farmer)
}