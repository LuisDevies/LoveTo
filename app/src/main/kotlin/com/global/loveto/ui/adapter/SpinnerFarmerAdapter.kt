package com.global.loveto.ui.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.global.loveto.R
import com.global.loveto.domain.model.Farmer

class SpinnerFarmerAdapter(
    context: Activity,
    resourceId: Int,
    textviewId: Int,
    list: MutableList<Farmer?>
) :
    ArrayAdapter<Farmer?>(context, resourceId, textviewId, list) {
    var flater: LayoutInflater = context.layoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowItem: Farmer? = getItem(position)
        val rowview: View = flater.inflate(R.layout.item_spinner_farmer, null, true)
        val txtTitle = rowview.findViewById(R.id.tv_farmer_name) as TextView
        txtTitle.text = rowItem?.name ?: ""
        if (position == 0) {
            // Set the hint text color gray
            txtTitle.text = context.getString(R.string.choose_farmer)
            txtTitle.setTextColor(Color.GRAY);
        }
        return rowview
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowview: View = flater.inflate(R.layout.item_spinner_farmer, null, true)
        val txtTitle = rowview.findViewById(R.id.tv_farmer_name) as TextView
        if (position == 0) {
            // Set the hint text color gray
            txtTitle.text = context.getString(R.string.choose_farmer)
            txtTitle.setTextColor(Color.GRAY);
        } else {
            txtTitle.setTextColor(Color.BLACK);
            val rowItem: Farmer? = getItem(position)
            txtTitle.text = rowItem?.name ?: ""
        }

        return rowview
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }


}