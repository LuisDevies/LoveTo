package com.global.loveto.ui.activity

import android.content.Context
import android.content.Intent
import com.global.loveto.R
import com.global.loveto.platform.BaseActivityFragment
import com.global.loveto.ui.fragment.LoginFragment

class LoginActivity : BaseActivityFragment() {

    companion object {
        fun callingIntent(context: Context?) = Intent(context, LoginActivity::class.java)
    }

    override fun layoutId() = R.layout.activity_layout_no_toolbar

    override fun fragment() = LoginFragment.newInstance()

}
