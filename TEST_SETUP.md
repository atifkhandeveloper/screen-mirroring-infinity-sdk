# Screen Mirroring - Test Setup

This project is configured for Android Studio with Kotlin + XML (no Compose).

## AdMob
The included AdMob application and ad-unit IDs are Google's official test IDs. Replace them in `AndroidManifest.xml` and `AdAdmob.java` before production release.

## Google Play Billing
Billing is already integrated using Google Play Billing Library 8.0.0. The placeholder subscription product ID is:

`screen_mirroring_weekly_test`

Create a matching subscription product in Play Console (including a base plan/offer), then replace `WEEKLY_PRODUCT_ID` in `BillingRepository.kt` with your production product ID. Billing products cannot be replaced by a universal Google test ID.

## Firebase
The original `google-services.json` is retained so the existing Firebase Analytics integration is preserved. Replace it with your own Firebase configuration if this project is being moved to a different Firebase project.

## Build
Open the project root in Android Studio and let Gradle sync. The project uses Gradle 8.13, AGP 8.13.2, Kotlin 2.2.0, compile/target SDK 36, and Java/Kotlin JVM target 11.
