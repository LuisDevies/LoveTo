/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.global.loveto.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class PermissionsHelper (private var applicationContext: Context) {

    private val context = applicationContext

    private fun isPermissionGranted(activity: Activity, permission: String) =
            activity.applicationContext.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED


    fun isStoragePermission(activity: Activity) =
            isPermissionGranted(activity, LoveToPermissions.WriteStorage.permission)


    fun requestStoragePermission(activity: Activity, permissionsCallback: PermissionsCallback) {

        val currentPermission = LoveToPermissions.WriteStorage.permission

        Dexter.withActivity(activity)
                .withPermission(currentPermission)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        permissionsCallback.permissionGranted(true, currentPermission)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        // check for permanent denial of permission
                        permissionsCallback.permissionGranted(false, currentPermission)
                        if (response.isPermanentlyDenied) {
                            // navigate user to app settings
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()

    }

   /* fun isAudioRecordPermission(activity: Activity) =
            isPermissionGranted(activity, LoveToPermissions.RecordAudio.permission)

    fun requestAudioRecordPermission(activity: Activity, permissionsCallback: PermissionsCallback) {

        val currentPermission = LoveToPermissions.RecordAudio.permission

        Dexter.withActivity(activity)
                .withPermission(currentPermission)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        logd("granted")
                        permissionsCallback.permissionGranted(true, currentPermission)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        // check for permanent denial of permission
                        logd("denied")
                        permissionsCallback.permissionGranted(false, currentPermission)
                        if (response.isPermanentlyDenied) {
                            // navigate user to app settings
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        logd("rationale")
                        token.continuePermissionRequest()
                    }
                }).check()

    }

    fun isLocationPermissions(activity: Activity) =
            isPermissionGranted(activity, InstantgoPermissions.Location.permission)

    fun requestLocationPermissions(activity: Activity, permissionsCallback: PermissionsCallback) {

        val currentPermission = InstantgoPermissions.Location.permission

        Dexter.withActivity(activity)
                .withPermission(currentPermission)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        logd("granted")
                        permissionsCallback.permissionGranted(true, currentPermission)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        // check for permanent denial of permission
                        logd("denied")
                        permissionsCallback.permissionGranted(false, currentPermission)
                        if (response.isPermanentlyDenied) {
                            // navigate user to app settings
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        logd("rationale")
                        token.continuePermissionRequest()
                    }
                }).check()

    }

    fun isContactsPermission(activity: Activity) =
            isPermissionGranted(activity, InstantgoPermissions.ReadContacts.permission)

    fun requestContactsPermission(activity: Activity, permissionsCallback: PermissionsCallback) {

        val currentPermission = InstantgoPermissions.ReadContacts.permission

        Dexter.withActivity(activity)
                .withPermission(currentPermission)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        logd("granted")
                        permissionsCallback.permissionGranted(true, currentPermission)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        // check for permanent denial of permission
                        logd("denied")
                        permissionsCallback.permissionGranted(false, currentPermission)
                        if (response.isPermanentlyDenied) {
                            // navigate user to app settings
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        logd("rationale")
                        token.continuePermissionRequest()
                    }
                }).check()
    }*/
}


enum class LoveToPermissions(val permission: String) {
    WriteStorage(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
    /*RecordAudio(android.Manifest.permission.RECORD_AUDIO),
    Location(android.Manifest.permission.ACCESS_COARSE_LOCATION),
    ReadContacts(android.Manifest.permission.READ_CONTACTS),*/
}