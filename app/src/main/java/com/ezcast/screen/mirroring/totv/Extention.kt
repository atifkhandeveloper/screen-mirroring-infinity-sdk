package com.ezcast.screen.mirroring.totv

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams

fun AppCompatActivity.edgeToEdge(view: View) {

    // ── Required: tell the window to draw behind system bars ──────────────────
    WindowCompat.setDecorFitsSystemWindows(window, false)

    // ── Apply all system bar insets (status bar TOP + nav bar BOTTOM) ─────────
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
        val insets = windowInsets.getInsets(
            WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.displayCutout()   // handles notch/punch-hole too
        )
        v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin    = insets.top       // ← this was missing — fixes status bar
            leftMargin   = insets.left
            rightMargin  = insets.right
            bottomMargin = insets.bottom
        }
        WindowInsetsCompat.CONSUMED
    }
}