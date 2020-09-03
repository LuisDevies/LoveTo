package com.global.loveto.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.global.loveto.R
import com.global.loveto.core.enums.Operation
import com.global.loveto.databinding.FragmentFarmerDetailBinding
import com.global.loveto.domain.model.Farmer
import com.global.loveto.platform.BaseFragment
import kotlinx.android.synthetic.main.toolbar_back.*


class FarmerDetailFragment : BaseFragment() {

    private lateinit var operation: Operation
    private lateinit var farmer: Farmer
    private lateinit var binding: FragmentFarmerDetailBinding

    companion object {
        private const val operation_tag = "operation_tag"
        private const val farmer_tag = "farmer_tag"

        fun newInstance(operation: Operation, farmer: Farmer): FarmerDetailFragment {
            val fragment = FarmerDetailFragment()
            val arguments = Bundle()
            arguments.putSerializable(operation_tag, operation)
            arguments.putSerializable(farmer_tag, farmer)
            fragment.arguments = arguments

            return fragment
        }
    }

    override fun layoutId() = R.layout.fragment_farmer_detail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFarmerDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }


    override fun initializeView() {

        operation = arguments?.getSerializable(operation_tag) as Operation
        farmer = arguments?.getSerializable(farmer_tag) as Farmer

        when (operation) {
            Operation.CLAIM -> binding.title = getString(R.string.claim)
            Operation.AGREEMENT -> binding.title = getString(R.string.agreement)
        }

        binding.farmer = farmer
        binding.executePendingBindings()
    }

    override fun initializeListeners() {

        toolbar_back_arrow.setOnClickListener {
            navigator.goToHome(context)
        }

        binding.btCancel.setOnClickListener { goBack() }

        binding.btSelect.setOnClickListener {

            when (operation) {
                Operation.CLAIM -> navigator.goToClaim(context,farmer)
                Operation.AGREEMENT -> navigator.goToAgreement(context,farmer)
            }

        }

    }

}
