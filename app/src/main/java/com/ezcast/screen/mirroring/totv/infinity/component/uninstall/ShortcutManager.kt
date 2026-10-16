package com.ezcast.screen.mirroring.totv.infinity.component.uninstall

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import com.ezcast.screen.mirroring.totv.R
import com.ezcast.screen.mirroring.totv.app.AppConstants
import com.ezcast.screen.mirroring.totv.infinity.component.splash.SplashActivity
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.getSystemLocaleString

object ShortcutManager {
    fun initShortCut(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val manager = context.getSystemService(ShortcutManager::class.java)
            try {
                manager.removeAllDynamicShortcuts()
                val uninstallShortCut = ShortcutInfo.Builder(context, AppConstants.ACTION_OPEN_UNINSTALL)
                    .setShortLabel(context.getSystemLocaleString(R.string.txt_uninstall))
                    .setIcon(Icon.createWithResource(context, R.drawable.ic_uninstall))
                    .setIntent(Intent(context, SplashActivity::class.java).apply {
//                        Intent.setFlags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                        Intent.setAction = "android.intent.action.SHORTCUT_UNINSTALL_APP"
                        putExtra(AppConstants.FROM_SHORTCUT, AppConstants.ACTION_OPEN_UNINSTALL)
                    })
                    .setRank(1)
                    .build()
                manager.dynamicShortcuts = listOf(uninstallShortCut)
            } catch (_: Exception) {
            }
        }
    }
}
