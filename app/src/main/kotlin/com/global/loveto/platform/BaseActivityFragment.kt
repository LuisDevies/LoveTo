package com.global.loveto.platform

import android.os.Bundle
import com.global.loveto.R
import com.global.loveto.R.id
import com.global.loveto.core.extension.inTransaction

/**
 * Base Activity class with helper methods for handling fragment transactions and ic_back button
 * events.
 *
 *
 */
abstract class BaseActivityFragment : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale)
        addFragment(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate)
    }

    override fun layoutId() = R.layout.activity_layout

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
                id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) =
            savedInstanceState ?: supportFragmentManager.inTransaction {
                add(id.fragmentContainer, fragment())
            }

    abstract fun fragment(): BaseFragment
}
