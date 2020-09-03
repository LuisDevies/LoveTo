package com.global.loveto.permissions

interface PermissionsCallback {
    fun permissionGranted(granted: Boolean, permissionTag: String)
}