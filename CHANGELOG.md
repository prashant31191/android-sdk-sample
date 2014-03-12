LiquidM SDK Changelog
===

5.2.1 (2014-03-12)
---

- Fixed problem with parsing MRAID calendar event json.
- Fixed mraid.useCustomClose().
- Avoid ad view zooming happening when entering text.

5.2.0 (2014-03-07)
---

- Added TestApp application.

5.1.2 (2014-03-06)
---

- Fixed problem with HD banners occuring on HDPI devices.


5.1.1 (2014-02-25)
---

- Fixed problem with multiple simultaneous ad requests.

5.1.0 (2014-02-21)
---

- Various fixes and improvements in MRAID implementation.
- Fixed mraid.createCalendarEvent() click notification.
- Fixed mraid.storePicture() on Nexus 7.

5.0.6 (2014-02-12)
---

- Support ads that open content in separate browser windows.
- Avoid scrolling in image ads.

5.0.5 (2014-02-10)
---

- Fixed fullscreen ad orientation handling.
- Fixed mediation adapter onPresentScreen, onDismissScreen, onClick notifications.
- Video pre-rolls and post rolls are now shown only once.
- Fixed video ad glitches.
- Updated example application icons.

5.0.4 (2014-02-05)
---

- Added support for HD banners.
- Fixed text overflowing in text ads.
- VideoAdView now allows to retry playing video after an error has occured.
- VideoAdView now skips to last known position after it recovered from error.
- Video ad won't show "Skip in 00 secs" ever again.
- Fixed video ad crashes on Samsung Galaxy Note.
- Fixed mediation adapter onLeaveApplicaton notification.

5.0.3 (2014-01-30)
---

- Created AdMob mediation adapter.
- Fixed MRAID initialization on Android devices with API < 11.

5.0.2 (2014-01-17)
---

- Minor fixes in SDK.

5.0.0 (2014-01-16)
---

- Added video ads support.

4.0.0 (2013-11-22)
---

- Added MRAID 2.0 support.
- Created example application.
- Added support for custom size banners.
