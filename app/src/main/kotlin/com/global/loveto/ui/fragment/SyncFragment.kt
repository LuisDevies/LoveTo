package com.global.loveto.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.global.loveto.R
import com.global.loveto.core.coroutines.Completable
import com.global.loveto.platform.BaseFragment
import com.global.loveto.core.coroutines.Result
import com.global.loveto.core.extension.LiveCompletable
import com.global.loveto.core.extension.isNetworkAvailable
import com.global.loveto.core.extension.isWifiAvailable
import com.global.loveto.core.extension.observe
import com.global.loveto.domain.model.*
import com.global.loveto.preferences.SharedPreferencesHelper
import com.global.loveto.presentation.AgreementViewModel
import com.global.loveto.presentation.ClaimViewModel
import com.global.loveto.presentation.OperationsViewModel
import com.global.loveto.ui.adapter.RecyclerOperationsAdapter
import kotlinx.android.synthetic.main.fragment_sync.*
import kotlinx.android.synthetic.main.toolbar_back.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SyncFragment : BaseFragment() {

    private var operations: List<Operation> = listOf()

    private var claimToSave: Claim? = null

    private var agreementToSave: Agreement? = null

    lateinit var adapter: RecyclerOperationsAdapter

    private val operationsViewModel: OperationsViewModel by viewModel()

    private val claimViewModel: ClaimViewModel by viewModel()

    private val agreementViewModel: AgreementViewModel by viewModel()

    companion object {
        fun newInstance() = SyncFragment()
    }

    override fun layoutId() = R.layout.fragment_sync

    override fun initializeView() {
        with(operationsViewModel) {
            observe(operations, ::handleOperations)
            observe(remoteOperations, ::handleSyncedOperations)
            observe(deleteCompletable, ::handleDeleteSyncedOperations)
            observe(localOperationsCompletable, ::handleSaveSyncedOperations)
            showLoading()
            if (connectionManager.isNetworkAvailable()) {
                getSyncedOperations(SharedPreferencesHelper.userId!!)
            } else {
                getLocalOperations()
            }
        }

        with(claimViewModel) {
            observe(any, ::handleClaimSynced)
        }

        with(agreementViewModel) {
            observe(any, ::handleAgreementSynced)
        }
    }

    private fun handleSyncedOperations(result: Result<List<Operation>>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                operations = result.value
                operationsViewModel.deleteSyncedOperations()
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

    private fun handleDeleteSyncedOperations(result: Completable?) {
        when (result) {
            is Completable.OnLoading -> showLoading()
            is Completable.OnComplete -> {
                operationsViewModel.saveLocalOperations(operations)
            }
            is Completable.OnError -> {
                hideLoading()
                notify(R.string.error)
            }
            else -> {
                hideLoading()
                notify(R.string.error)
            }
        }

    }

    private fun handleSaveSyncedOperations(result: Completable?) {
        when (result) {
            is Completable.OnLoading -> showLoading()
            is Completable.OnComplete -> {
                operationsViewModel.getLocalOperations()
            }
            is Completable.OnError -> {
                hideLoading()
                notify(R.string.error)
            }
            else -> {
                hideLoading()
                notify(R.string.error)
            }
        }

    }

    private fun handleClaimSynced(result: Result<Any>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                hideLoading()
                claimToSave?.synced = true
                claimViewModel.saveLocalClaim(claimToSave!!.toOperationEntity())
                operations[operations.indexOfFirst { it.id == claimToSave!!.id }].synced = true
                adapter.notifyDataSetChanged()
                claimToSave = null
                sync()
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

    private fun handleAgreementSynced(result: Result<Any>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                hideLoading()
                agreementToSave!!.synced = true
                agreementViewModel.saveLocalAgreement(agreementToSave!!.toOperationEntity())
                operations[operations.indexOfFirst { it.id == agreementToSave!!.id }].synced = true
                adapter.notifyDataSetChanged()
                agreementToSave = null
                sync()
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

    override fun initializeListeners() {
        toolbar_back_arrow.setOnClickListener { navigator.goToHome(context) }
        bt_back.setOnClickListener { navigator.goToHome(context) }
        bt_sync.setOnClickListener {
            if (connectionManager.isNetworkAvailable() && connectionManager.isWifiAvailable()) {
                sync()
            } else {
                showMessageDialog(
                    R.string.information_will_sync_when_there_is_wifi,
                    R.drawable.blue_exclamation_dark
                )
            }
        }

    }

    private fun sync() {
        for (operation: Operation in operations) {

            if (!operation.synced) {
                when (operation.operation) {
                    com.global.loveto.core.enums.Operation.CLAIM -> {
                        claimToSave = operation.toClaim()
                        claimViewModel.saveRemoteClaim(
                            claimToSave!!
                        )

                    }
                    else -> {
                        agreementToSave = operation.toAgreement()
                        agreementViewModel.saveRemoteAgreement(agreementToSave!!)
                    }
                }
                break
            }

        }

    }

    private fun handleOperations(result: Result<List<Operation>>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                hideLoading()
                operations = result.value.toMutableList()
                adapter = RecyclerOperationsAdapter(operations)
                rv_operation.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = this@SyncFragment.adapter
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


}
