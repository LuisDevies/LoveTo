package com.global.loveto.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.global.loveto.R
import com.global.loveto.core.enums.Operation
import com.global.loveto.platform.BaseActivityFragment
import com.global.loveto.ui.fragment.HomeFragment
import com.global.loveto.ui.fragment.SelectFarmerFragment

class SelectFarmerActivity : BaseActivityFragment() {

    lateinit var operation : Operation

    companion object {

        private const val operation_tag = "text_tag"

        fun callingIntent(context: Context?, operation : Operation): Intent {
            val intent = Intent(context, SelectFarmerActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(operation_tag, operation)
            intent.putExtras(bundle)

            return intent

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        operation = intent.extras?.getSerializable(operation_tag) as Operation
        super.onCreate(savedInstanceState)

    }

    override fun layoutId() = R.layout.activity_layout_back

    override fun fragment() = SelectFarmerFragment.newInstance(operation)
}
