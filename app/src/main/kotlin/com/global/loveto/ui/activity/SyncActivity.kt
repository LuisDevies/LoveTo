package com.global.loveto.ui.activity

import android.content.Context
import android.content.Intent
import com.global.loveto.R
import com.global.loveto.platform.BaseActivityFragment
import com.global.loveto.ui.fragment.SyncFragment

class SyncActivity : BaseActivityFragment() {

    companion object {


        fun callingIntent(context: Context?) = Intent(context, SyncActivity::class.java)

    }

    override fun layoutId() = R.layout.activity_layout_back

    override fun fragment() = SyncFragment.newInstance()
}