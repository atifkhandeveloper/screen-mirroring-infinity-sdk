package com.ezcast.screen.mirroring.totv.infinity.component.main.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.ezcast.screen.mirroring.totv.BuildConfig
import com.ezcast.screen.mirroring.totv.R
import com.ezcast.screen.mirroring.totv.infinity.data.model.ForceUpdateConfig
import com.ezcast.screen.mirroring.totv.databinding.DialogForceUpdateBinding
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.click
import timber.log.Timber

class ForceUpdateDialog(private val activity: Activity) {
    private var dialog: Dialog? = null

    fun show(config: ForceUpdateConfig) {
        if (dialog?.isShowing == true) return
        val binding = DialogForceUpdateBinding.inflate(activity.layoutInflater)
        val alertDialog = Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        alertDialog.setContentView(binding.root)
        alertDialog.setCancelable(!config.force)

        binding.txtForceUpdateTitle.text =
            config.title.ifBlank { activity.getString(R.string.app_name) }
        binding.txtForceUpdateDescription.text =
            config.description.ifBlank { activity.getString(R.string.force_update_message) }

        if (config.icon.isNotBlank()) {
            Glide.with(activity)
                .load(config.icon)
                .placeholder(R.drawable.icon)
                .into(binding.imgForceUpdate)
        } else {
            binding.imgForceUpdate.setImageResource(R.drawable.icon)
        }

        binding.btnUpdateNow.click {
            val finalLink = config.storeLink.ifBlank { defaultStoreLink() }
            openStoreLink(finalLink, config.force.not(), alertDialog)
        }

        alertDialog.show()
        dialog = alertDialog
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }

    private fun openStoreLink(link: String, canDismiss: Boolean, alertDialog: Dialog?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
            if (canDismiss) {
                alertDialog?.dismiss()
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    private fun defaultStoreLink(): String =
        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
}

