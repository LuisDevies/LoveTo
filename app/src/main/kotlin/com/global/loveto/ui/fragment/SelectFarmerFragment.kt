package com.global.loveto.ui.fragment


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.global.loveto.R
import com.global.loveto.core.coroutines.Result
import com.global.loveto.core.enums.Operation
import com.global.loveto.core.extension.observe
import com.global.loveto.domain.model.Farmer
import com.global.loveto.platform.BaseFragment
import com.global.loveto.preferences.SharedPreferencesHelper
import com.global.loveto.presentation.FarmersViewModel
import com.global.loveto.ui.adapter.SpinnerFarmerAdapter
import kotlinx.android.synthetic.main.fragment_select_farmer.*
import kotlinx.android.synthetic.main.toolbar_back.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectFarmerFragment : BaseFragment() {

    private lateinit var operation: Operation
    private val farmerViewModel: FarmersViewModel by viewModel()

    companion object {
        private const val operation_tag = "operation_tag"

        fun newInstance(operation: Operation): SelectFarmerFragment {
            val fragment = SelectFarmerFragment()
            val arguments = Bundle()
            arguments.putSerializable(operation_tag, operation)
            fragment.arguments = arguments

            return fragment
        }
    }

    override fun layoutId() = R.layout.fragment_select_farmer

    override fun initializeView() {

        operation = arguments?.getSerializable(operation_tag) as Operation

        when (operation) {
            Operation.CLAIM -> tv_title.text = getString(R.string.claim)
            Operation.AGREEMENT -> tv_title.text = getString(R.string.agreement)
        }

        with(farmerViewModel) {
            observe(farmers, ::handleFarmers)
            showLoading()
            SharedPreferencesHelper.userId?.let { getFarmers(it) }
        }


    }

    private fun handleFarmers(result: Result<List<Farmer>>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                hideLoading()
                val farmers = result.value.toMutableList()
                farmers.add(0, Farmer(name = getString(R.string.choose_farmer)))
                val adapter =
                    activity?.let {
                        SpinnerFarmerAdapter(
                            it, R.layout.item_spinner_farmer, R.id.tv_farmer_name,
                            farmers.toMutableList()
                        )
                    }
                sp_farmers.adapter = adapter
                sp_farmers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position > 0) {
                            onFarmerSelected(parent?.getItemAtPosition(position) as Farmer)
                        }
                    }

                }
            }
            is Result.OnError -> {
                hideLoading()
                notify(R.string.error)
            }
            else -> {
                hideLoading()
                notify(R.string.error)
            }
        }

    }

    private fun onFarmerSelected(farmer: Farmer) {
        navigator.goToFarmerDetail(context, operation, farmer)
    }

    override fun initializeListeners() {

        toolbar_back_arrow.setOnClickListener { goBack() }

    }


}
