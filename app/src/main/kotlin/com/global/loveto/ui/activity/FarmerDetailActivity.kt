package com.global.loveto.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.global.loveto.R
import com.global.loveto.core.enums.Operation
import com.global.loveto.domain.model.Farmer
import com.global.loveto.platform.BaseActivityFragment
import com.global.loveto.ui.fragment.FarmerDetailFragment

class FarmerDetailActivity : BaseActivityFragment() {

    lateinit var operation : Operation
    lateinit var farmer : Farmer

    companion object {

        private const val operation_tag = "text_tag"
        private const val farmer_tag = "farmer_tag"

        fun callingIntent(context: Context?, operation : Operation, farmer : Farmer): Intent {
            val intent = Intent(context, FarmerDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(operation_tag, operation)
            bundle.putSerializable(farmer_tag, farmer)
            intent.putExtras(bundle)

            return intent

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        operation = intent.extras?.getSerializable(operation_tag) as Operation
        farmer = intent.extras?.getSerializable(farmer_tag) as Farmer
        super.onCreate(savedInstanceState)

    }

    override fun layoutId() = R.layout.activity_layout_back

    override fun fragment() = FarmerDetailFragment.newInstance(operation,farmer)
}