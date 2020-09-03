package com.global.loveto.platform

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64.DEFAULT
import android.util.Base64OutputStream
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.global.loveto.LoveToApp
import com.global.loveto.R
import com.global.loveto.core.extension.*

import com.global.loveto.navigation.Navigator
import com.global.loveto.permissions.PermissionsCallback
import com.global.loveto.permissions.PermissionsHelper
import com.global.loveto.preferences.SharedPreferencesHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import java.io.*
import java.util.*

/**
 * Base Fragment class with helper methods for handling views and ic_back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : Fragment(), Serializable {

    protected var mBottomSheetDialog: BottomSheetDialog? = null
    protected var sheetView: View? = null
    protected var imageVideoFile: File? = null
    protected val connectionManager: ConnectivityManager by inject()

    private val permissionsCallback = object : PermissionsCallback {
        override fun permissionGranted(granted: Boolean, permissionTag: String) {
            permissionRequestResult(granted, permissionTag)
        }
    }

    /*protected val dateFormat = android.text.format.DateFormat.getBestDateTimePattern(Locale.getDefault(), "ddmmssHHMMMyyyy")*/

    abstract fun layoutId(): Int

    open fun permissionRequestResult(granted: Boolean, permissionTag: String) =
        logd(granted, this.javaClass.name)


    protected var notificationManager: NotificationManager? = null

    val navigator: Navigator by inject()


    val permissionsHelper: PermissionsHelper by inject()

    /*@Inject
    lateinit var imageLoader: ImageLoader*/

    internal fun isStoragePermission() =
        permissionsHelper.isStoragePermission(activity as Activity)

    internal fun requestStoragePermission(activity: Activity) {
        permissionsHelper.requestStoragePermission(activity as Activity, permissionsCallback)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
        initializeListeners()

    }

    abstract fun initializeView()

    abstract fun initializeListeners()

    open fun onBackPressed() {}

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun showLoading(progress: Int? = null) {
        if (activity is BaseActivity)
            (activity as BaseActivity).showLoading(progress)
    }

    internal fun hideLoading() {
        if (activity is BaseActivity)
            (activity as BaseActivity).hideLoading()
    }

    internal fun showMessageDialog(textResource : Int, iconResource : Int) {
        if (activity is BaseActivity)
            (activity as BaseActivity).showMessageDialog(textResource,iconResource)
    }

    internal fun hideMessageDialog() {
        if (activity is BaseActivity)
            (activity as BaseActivity).hideMessageDialog()
    }

    internal fun hideKeyboard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
        } else {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }

    internal fun showKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    internal fun showErrorAlert(stringRes: Int) {
        showAlertDialog {
            setTitle(R.string.app_name)
            setMessage(stringRes)
            this.positiveButton(getString(R.string.ok))
        }
    }

    internal fun showErrorAlert(text: String) {
        showAlertDialog {
            setTitle(R.string.app_name)
            setMessage(text)
            this.positiveButton(getString(R.string.ok))
        }
    }

    fun showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
        try {
            val builder = AlertDialog.Builder(activity)
            builder.dialogBuilder()
            val dialog = builder.create()

            dialog.show()
        } catch (exce: Exception) {
            logw(exce.localizedMessage)
        }
    }

    internal fun goBack() {
        navigator.goBack(activity)
    }

    internal fun notify(@StringRes message: Int, lenghtLong: Boolean = false) {
        val lenght = if (lenghtLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        Snackbar.make(viewContainer, message, lenght).setTextColor(Color.WHITE).show()
    }

    internal fun notify(message: String, lenghtLong: Boolean = false) {
        val lenght = if (lenghtLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        Snackbar.make(viewContainer, message, lenght).setTextColor(Color.WHITE).show()
    }


    internal fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_LONG)
        snackBar.setTextColor(Color.WHITE).show()
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(Color.YELLOW)
        snackBar.show()
    }

    internal fun fragmentTag() {
        javaClass::class.java.simpleName
    }

    @Throws(IOException::class)
    protected fun createImageFile() {
        // Create an image file name
        val timeStamp =
            getCurrentDateString("yyyyMMdd_HHmmss")
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        var mCurrentPhotoPath = image.absolutePath
        imageVideoFile = image
    }

    protected fun createImageFromBitmap(bitmap: Bitmap) {
        createImageFile()
        val os: OutputStream = BufferedOutputStream(FileOutputStream(imageVideoFile))
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        os.close()
    }

    @Throws(IOException::class)
    protected fun createVideoFile() {
        // Create an image file name
        val timeStamp =
            getCurrentDateString("yyyyMMdd_HHmmss")
        val imageFileName = SharedPreferencesHelper.userId + "VID_" + timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".mp4", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        var mCurrentPhotoPath = image.absolutePath
        imageVideoFile = image
    }

    fun convertImageFileToBase64(imageFile: File): String {
        return FileInputStream(imageFile).use { inputStream ->
            ByteArrayOutputStream().use { outputStream ->
                Base64OutputStream(outputStream, DEFAULT).use { base64FilterStream ->
                    inputStream.copyTo(base64FilterStream)
                    base64FilterStream.close()
                    outputStream.toString()
                }
            }
        }
    }

    @Throws(IOException::class)
    protected fun createDocumentFile() {
        // Create an image file name
        val timeStamp =
            getCurrentDateString("yyyyMMdd_HHmmss")
        val imageFileName = timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".pdf", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        var mCurrentPhotoPath = image.absolutePath
        imageVideoFile = image
    }

    internal fun getRealPathFromUri(contentUri: Uri?): String? {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Video.Media.DATA)
            cursor = context?.contentResolver?.query(contentUri, proj, null, null, null)
            val column_index: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor?.moveToFirst()
            return cursor?.getString(column_index ?: 0)
        } finally {
            cursor?.close()
        }
    }

    internal fun getCurrentLocale(context: Context?): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context?.resources?.configuration?.locales?.get(0) ?: Locale.getDefault()
        } else {
            context?.resources?.configuration?.locale ?: Locale.getDefault()
        }
    }

    internal fun shareBitmap(image: Bitmap) {
        try {
            val file = File(context?.externalCacheDir, "tempImage.png")
            val fOut = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            val intent = Intent(android.content.Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: Exception) {
            notify(R.string.error)
            e.printStackTrace()
        }
    }

/*    internal fun isStoragePermission() =
            permissionsHelper.isStoragePermission(activity as Activity)

    internal fun requestStoragePermission(activity: Activity) {
        permissionsHelper.requestStoragePermission(activity as Activity, permissionsCallback)
    }

    internal fun isAudioRecordPermission() =
            permissionsHelper.isAudioRecordPermission(activity as Activity)

    internal fun requestVideoRecordPermission(activity: Activity) {
        permissionsHelper.requestVideoRecordPermission(activity as Activity, permissionsCallback)
    }

    internal fun isLocationPermissions() =
            permissionsHelper.isLocationPermissions(activity as Activity)

    internal fun requestLocationPermissions(activity: Activity) {
        permissionsHelper.requestLocationPermissions(activity as Activity, permissionsCallback)
    }*/

/*    internal fun isContactsPermission() =
            permissionsHelper.isContactsPermission(activity as Activity)

    internal fun requestContactsPermission(activity: Activity) {
        permissionsHelper.requestContactsPermission(activity as Activity, permissionsCallback)
    }*/

    fun refreshSessionScope() {
        (activity?.application as? LoveToApp)?.refreshSessionScope()
    }

}