package com.global.loveto.platform

import android.os.Bundle
import com.global.loveto.R
import kotlinx.android.synthetic.main.toolbar_back.*

/**
 * Base Activity class with helper methods for handling fragment transactions and ic_back button
 * events.
 *
 */
abstract class BaseActivityFragmentBack : BaseActivityFragment() {
    override fun layoutId() = R.layout.activity_layout_back

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar_back_arrow.setOnClickListener { onBackPressed() }
    }
}
