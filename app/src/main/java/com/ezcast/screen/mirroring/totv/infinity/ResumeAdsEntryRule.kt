package com.ezcast.screen.mirroring.totv.infinity


import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig
import com.ezcast.screen.mirroring.totv.infinity.ads.inter_welcome
import com.ezcast.screen.mirroring.totv.infinity.ads.native_welcome
import com.ezcast.screen.mirroring.totv.infinity.ads.open_resume

enum class ResumeAdsEntryMode {
    OPEN_RESUME,
    WELCOME,
    NONE,
}

object ResumeAdsEntryRule {
    fun currentMode(): ResumeAdsEntryMode {
        if (!AdRemoteConfig.isInitialized()) return ResumeAdsEntryMode.NONE

        val canUseOpenResume = AdRemoteConfig.open_resume.isEnable
        if (canUseOpenResume) return ResumeAdsEntryMode.OPEN_RESUME

        val canUseWelcome = AdRemoteConfig.native_welcome.isEnable && AdRemoteConfig.inter_welcome.isEnable
        return if (canUseWelcome) ResumeAdsEntryMode.WELCOME else ResumeAdsEntryMode.NONE
    }

    fun shouldEnableOpenResume(): Boolean = currentMode() == ResumeAdsEntryMode.OPEN_RESUME

    fun shouldShowWelcomeOnResume(): Boolean =
        currentMode() == ResumeAdsEntryMode.WELCOME && !AdRemoteConfig.open_resume.isEnable
}
