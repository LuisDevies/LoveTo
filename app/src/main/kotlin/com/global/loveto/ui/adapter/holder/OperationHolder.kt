package com.global.loveto.ui.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.global.loveto.R
import com.global.loveto.domain.model.Operation

class OperationHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_operation, parent, false)) {
    private var mFarmer: TextView? = null
    private var mType: TextView? = null
    private var mSync: ImageView? = null


    init {
        mFarmer = itemView.findViewById(R.id.tv_item_farmer)
        mType = itemView.findViewById(R.id.tv_item_type)
        mSync = itemView.findViewById(R.id.iv_item_sync)
    }

    fun bind(operation: Operation) {
        mFarmer?.text = operation.farmerNumber
        mType?.text = when (operation.operation) {
            com.global.loveto.core.enums.Operation.CLAIM -> "CL"
            else -> "AG"
        }
        mSync?.setImageResource(
            if (operation.synced) {
                R.drawable.synced
            } else {
                R.drawable.not_synced
            }
        )
    }

}