package com.global.loveto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.global.loveto.domain.model.Operation
import com.global.loveto.ui.adapter.holder.OperationHolder

class RecyclerOperationsAdapter(private val list: List<Operation>)
    : RecyclerView.Adapter<OperationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OperationHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: OperationHolder, position: Int) {
        val operation: Operation = list[position]
        holder.bind(operation)
    }

    override fun getItemCount(): Int = list.size

}