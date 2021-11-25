package com.dentag.educationproject

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

private const val SKYPE_PACKAGE = "com.skype.raider"
private const val SKYPE_PATH = "com.skype.raider.Main"

fun isSkypeInstalled(context: Context): Boolean {
    return try {
        context.packageManager.getPackageInfo(SKYPE_PACKAGE, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun openSkype(context: Context) {
    if (!isSkypeInstalled(context)) return

    val intent = Intent().apply {
        action = "android.intent.action.CALL_PRIVILEGED"
        data = Uri.parse("tel:")
        setClassName(SKYPE_PACKAGE, SKYPE_PATH)
    }

    context.startActivity(intent)
}