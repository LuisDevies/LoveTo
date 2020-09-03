package com.global.loveto.ui.fragment

import com.global.loveto.R
import com.global.loveto.core.extension.observe
import com.global.loveto.core.coroutines.Result
import com.global.loveto.core.extension.isNetworkAvailable
import com.global.loveto.data.request.LoginRequest
import com.global.loveto.domain.model.Farmer
import com.global.loveto.domain.model.User
import com.global.loveto.platform.BaseFragment
import com.global.loveto.preferences.SharedPreferencesHelper
import com.global.loveto.presentation.FarmersViewModel
import com.global.loveto.presentation.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment() {

    companion object {

        fun newInstance() = LoginFragment()

    }

    private val loginViewModel: LoginViewModel by viewModel()
    private val farmerViewModel: FarmersViewModel by viewModel()

    override fun layoutId() = R.layout.fragment_login

    override fun initializeView() {

        with(loginViewModel) {
            observe(userLogged, ::handleLogin)

        }
        with(farmerViewModel) {
            observe(farmers, ::handleFarmers)
        }

    }

    override fun initializeListeners() {
        bt_login.setOnClickListener {
            loginViewModel.login(
                LoginRequest(
                    et_email.text.toString(),
                    et_password.text.toString()
                )
            )
        }
    }

    private fun handleLogin(result: Result<User>?) {
        when (result) {
            is Result.OnLoading -> {
                showLoading()
            }
            is Result.OnSuccess -> {
                hideLoading()
                SharedPreferencesHelper.userId = result.value._id
                SharedPreferencesHelper.userName = result.value.name
                refreshSessionScope()
                farmerViewModel.getFarmers(result.value._id)
            }
            is Result.OnError -> {
                hideLoading()
                if (connectionManager.isNetworkAvailable())
                    showMessageDialog(
                        R.string.incorrect_login_password,
                        R.drawable.blue_exclamation_dark
                    )
                else {
                    showMessageDialog(
                        R.string.no_internet_access,
                        R.drawable.blue_exclamation_dark
                    )
                }

            }
            else -> {
                hideLoading()
                showMessageDialog(
                    R.string.incorrect_login_password,
                    R.drawable.blue_exclamation_dark
                )
            }
        }
    }

    private fun handleFarmers(result: Result<List<Farmer>>?) {
        when (result) {
            is Result.OnLoading -> showLoading()
            is Result.OnSuccess -> {
                hideLoading()
                navigator.goToHome(context)
            }
            is Result.OnError -> {
                hideLoading()
                showMessageDialog(
                    R.string.incorrect_login_password,
                    R.drawable.blue_exclamation_dark
                )
            }
            else -> {
                hideLoading()
                showMessageDialog(
                    R.string.incorrect_login_password,
                    R.drawable.blue_exclamation_dark
                )
            }
        }

    }

}
