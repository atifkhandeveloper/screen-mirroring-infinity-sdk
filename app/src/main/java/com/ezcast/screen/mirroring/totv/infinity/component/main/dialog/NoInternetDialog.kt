package com.ezcast.screen.mirroring.totv.infinity.component.main.dialog

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.provider.Settings
import com.ezcast.screen.mirroring.totv.databinding.DialogNoInternetBinding
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.click

class NoInternetDialog(private val activity: Activity) {
    private var dialog: Dialog? = null

    fun show() {
        if (dialog?.isShowing == true) return
        val binding = DialogNoInternetBinding.inflate(activity.layoutInflater)
        val alertDialog = Dialog(activity, R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        alertDialog.setContentView(binding.root)
        alertDialog.setCancelable(false)
        binding.btnOpenSettings.click {
            activity.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
        }
        alertDialog.show()
        dialog = alertDialog
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}

