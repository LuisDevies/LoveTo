package com.global.loveto.core.extension

import android.app.AlertDialog


fun AlertDialog.Builder.positiveButton(text: String = "Okay", handleClick: (which: Int) -> Unit = {}) {
    this.setPositiveButton(text) { dialogInterface, which -> handleClick(which) }
}

fun AlertDialog.Builder.neutralButton(text: String = "Neutral", handleClick: (which: Int) -> Unit = {}) {
    this.setPositiveButton(text) { dialogInterface, which -> handleClick(which) }
}

fun AlertDialog.Builder.negativeButton(text: String = "Cancel", handleClick: (which: Int) -> Unit = {}) {
    this.setNegativeButton(text) { dialogInterface, which -> handleClick(which) }
}