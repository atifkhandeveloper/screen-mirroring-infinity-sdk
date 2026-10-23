package com.ezcast.screen.mirroring.totv.infinity.component.onboarding.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

enum class FullNativeType { NONE, FULL1, FULL2 }

@Parcelize
data class OnboardingItem(
    @StringRes val title: Int = 0,
    @StringRes val description: Int = 0,
    @StringRes val textButton: Int = 0,
    @DrawableRes val imageResId: Int = 0,
    val positionIndicator: Int = -1,
    val isHasNativeOnPage1: Boolean = false,
    val isHasNativeOnPage4: Boolean = false,
    val isHasNativeFull: Boolean = false,
    val fullNativeType: FullNativeType = FullNativeType.NONE
) : Parcelable