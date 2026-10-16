# Google Cast test setup

The Cast framework configuration has been corrected.

## What was fixed
- `OPTIONS_PROVIDER_CLASS_NAME` is declared at the `<application>` level.
- The provider now points to the actual project class:
  `com.screen.mirroring.casting.sharing.tv.miracast.screenshare.Provider.CastOptionsProvider`
- Removed older explicit `play-services-cast:21.0.1` and `play-services-base:18.1.0` dependencies so they do not conflict with Cast Framework 22.2.0.
- The project continues to use Google's Default Media Receiver Application ID for standard media casting.

## Test
1. Connect the Android phone and Chromecast/Google TV to the same Wi-Fi network.
2. Build and install the debug app.
3. Open the casting screen and tap the Cast button.
4. Select the receiver.
5. Test a video, photo, and audio item.
6. Confirm playback controls work and that disconnecting stops the remote session.

## Important distinction
Google Cast here is for casting media to a Google Cast receiver. It does not implement Android device screen mirroring/Miracast. Those are separate protocols and require a different implementation.
