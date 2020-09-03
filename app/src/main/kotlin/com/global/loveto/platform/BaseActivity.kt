package com.global.loveto.platform

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.global.loveto.R
import com.global.loveto.core.extension.logw
import com.global.loveto.navigation.Navigator
import com.global.loveto.preferences.SharedPreferencesHelper
import org.koin.android.ext.android.inject


/**
 *
 * Base Activity class with helper methods for handling fragment transactions and ic_back button
 * events.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity() {


    internal val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        /*toolbar_title?.text = resources.getString(titleId())*/
    }


    protected abstract fun layoutId(): Int

    /*protected abstract fun titleId(): Int*/

    /* private fun handleFailure(failure: Failure?) {
         hideLoading()
         when (failure) {
             is Failure.NetworkConnection -> {
                 logd(R.string.failure_network_connection)
             }
             is Failure.ServerError -> {
                 logd(R.string.failure_server_error)
             }
         }
     }*/

    private var dialog: AlertDialog? = null
    private var errorDialog: AlertDialog? = null

    fun showLoading(progress: Int? = null) = setDialog(true)

    fun hideLoading() = setDialog(false)

    fun showMessageDialog(textResource: Int,iconResource: Int) = setMessageDialog(true,textResource,iconResource)

    fun hideMessageDialog() = setMessageDialog(false)

    private fun setDialog(show: Boolean) =
        try {
            if (dialog == null) {
                val builder = AlertDialog.Builder(this)
                builder.setView(R.layout.progress)
                builder.setCancelable(false)
                dialog = builder.create()
            }
            if (show)
                dialog?.show()
            else
                dialog?.dismiss()
        } catch (exc: Exception) {
            logw(exc.localizedMessage)
        }

    private fun setMessageDialog(show: Boolean,textResource : Int = R.string.app_name, iconResource : Int = R.drawable.blue_exclamation_dark) {
        try {
            if (errorDialog == null) {
                val builder = AlertDialog.Builder(this)
                val inflater = layoutInflater

                val customView: View = inflater.inflate(R.layout.modal_message, null)
                val messageView = customView.findViewById<TextView>(R.id.tv_message)
                val imageView = customView.findViewById<ImageView>(R.id.iv_icon)
                messageView.setText(textResource)
                imageView.setImageResource(iconResource)
                builder.setView(customView)
                builder.setCancelable(true)
                errorDialog = builder.create()
            }
            if (show)
                errorDialog?.show()
            else
                errorDialog?.dismiss()
        } catch (exc: Exception) {
            logw(exc.localizedMessage)
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    /*override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
                id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }*/
}
