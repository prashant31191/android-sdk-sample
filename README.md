# LiquidM Android SDK
The LiquidM Android SDK allows you to request and present ads in your app. It includes the newest mobile ad technology and provides the following features:

- Support for banner and interstitial ads
  - text, image and [MRAID 2.0](http://www.iab.net/media/file/IAB_MRAID_v2_FINAL.pdf) (interactive rich media creatives) ad formats
  - industry standard banners sizes: mma, medium_rectangle, etc. and custom sized banners
  - standard and HD image banners
- Support for video ads (based on [VAST](http://www.iab.net/media/file/VASTv3.0.pdf))
  - pre-roll and post-roll video ads
  - landscape-portrait auto adaptation
- Support for DFP mediation
  - dedicated adapter for [DFP Network Mediation](https://developers.google.com/mobile-ads-sdk/docs/dfp/mediation) integrated into SDK

# Repository structure
This repository contains an example app (LiquidMSDKExample) and provides all the files to integrate the LiquidM Android SDK in your app. We suggest that you clone this repository with git (git clone https://github.com/liquidm/android-sdk-sample.git). If you're not familiar with git you can download the content of this repository as zip archive ([download](https://github.com/liquidm/android-sdk-sample/archive/master.zip)).

After you cloned this repository or unzipped the archive you'll find the following structure:

- [SDK](SDK): Actual SDK with [DFP Network Mediation](https://developers.google.com/mobile-ads-sdk/docs/dfp/mediation) adapter.
- [API](Docs/API): Documentation of the public SDK interface.
- [Example](Example): Example application.
- [GooglePlayServices](GooglePlayServices): Google Play Services library needed by example application.
- [CHANGELOG.md](CHANGELOG.md): Documentation of SDK changes.
- [README.md](README.md): This documentation.

# Try the example application
First of all you should try to open our example app which contains the LiquidM Android SDK already integrated. To do so, just import the [example project](Example) into Eclipse, attach the Android device and run it.

![Example App Screenshot](Docs/Images/example-app-1.png "Example App Screenshot") ![Example App Screenshot](Docs/Images/example-app-2.png "Example App Screenshot")

In the example application you can see how to properly integrate:
- banner ad (both in code and xml)
- interstitial ad
- video ad (both in code and xml)

# Integrate the SDK into your application
1. Copy [liquidmsdk.jar](https://github.com/liquidm/android-sdk-sample/raw/master/SDK/liquidmsdk.jar) into libs/ directory of your project.
1. Set required permissions in AndroidManifest.xml
    ```xml
    <uses-permission android:name="android.permission.INTERNET"/>
    ```

1. Set optional permissions in AndroidManifest.xml

    Enable ```WRITE_EXTERNAL_STORAGE``` to allow MRAID ads store pictures on the device.

    ```xml
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    ```

    Enable ```ACCESS_COARSE_LOCATION``` or ```ACCESS_FINE_LOCATION``` to allow SDK gather device's location passively.
    
    ```xml
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    ```
    
    ```xml
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    ```

1. Define AdActivity in AndroidManifest.xml
    ```xml
    <activity
      android:name="com.liquidm.sdk.AdActivity"
      android:configChanges="keyboard|keyboardHidden|orientation|uiMode|screenLayout|screenSize|smallestScreenSize" />
    ```

1. Add Google Play Services to your project

  1. [Install the Google Play Services SDK](http://developer.android.com/google/play-services/setup.html#Install)

  1. [Integrate your project with Google Play Services SDK](http://developer.android.com/google/play-services/setup.html#Setup)

# Use cases
This section contains some common uses and describes how to integrate different kinds of ads into your applicaiton. For banners and video ads you can choose to integrate them in layout xml files or in application code. Please make sure, that you replace the "TestTokn" with your personal token. The "TestTokn" contains example ads and allows you to test your implementation.

## Request banner ad in xml
1. Add liquidm namespace declaration to xml root element.

    ```xml
    xmlns:liquidm="http://schemas.android.com/apk/lib/com.liquidm.sdk"
    ```

1. Add AdView to your layout.

    ```xml
    <!-- Replace TestTokn with your personal token -->
    <!-- For adSize use one of: mma, medium_rectangle, leaderboard, portrait, landscape, xx_large
      or provide a custom size, for example: 320x50.
      Note that the custom size is given in device-independent pixels. -->

    <com.liquidm.sdk.AdView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center|bottom"
      liquidm:adSize="mma"
      liquidm:autoreload="true"
      liquidm:siteToken="TestTokn" />
    ```

See [example layout](Example/res/layout/activity_banner_ad_from_xml.xml) for more details.

See also [How to select the best fitting banner size for most android devices?](#how-to-select-the-best-fitting-banner-size-for-most-android-devices)

## Request banner ad in code

```java
// Replace TestTokn with your personal token.
String siteToken = "TestTokn";

// Select desired ad size (MMA, MEDIUM_RECTANGLE, LEADERBOARD, PORTRAIT, LANDSCAPE, XX_LARGE)
AdSize adSize = AdSize.MMA;
// or request custom sized ad using:
// (note that the custom size is given in device-independent pixels)
// AdSize adSize = new AdSize(320, 50);

AdView adView = new AdView(this, siteToken, adSize);

adView.setAutoreload(true);

layout.addView(adView, new FrameLayout.LayoutParams(
  LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
  Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL));
```

See [example code](Example/src/com/liquidm/sdk/example/BannerAdFromCodeActivity.java) for more details.

See also [How to select the best fitting banner size for most android devices?](#how-to-select-the-best-fitting-banner-size-for-most-android-devices)

## Request interstitial ad and show it if ready.
1. Create and load InterstitialAd

    ```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      // ...

      // Replace TestTokn with your personal token.
      String siteToken = "TestTokn";

      InterstitialAd interstitialAd = new InterstitialAd(this, siteToken);

      interstitialAd.loadAd();
    }
    ```

1. Show interstitial ad

    ```java
    private void showInterstitial() {
      if (interstitial.isReady()) {
        interstitial.show();
      }
    }
    ```

## Request interstitial ad and show it as soon as it is loaded
1. Create InterstitialAd, set its listener, and load it.

    ```java
    @Override
	  protected void onCreate(Bundle savedInstanceState) {
      // ..

      // Replace TestTokn with your personal token.
      String siteToken = "TestTokn";
      InterstitialAd interstitialAd = new InterstitialAd(this, SITE_TOKEN);
      interstitialAd.setListener(this);

      interstitialAd.loadAd();
    }
    ```

1. Show interstitial ad in onAdLoad() event handler. Handle onAdFailedToLoad() event if needed.

    ```java
    @Override
    public void onAdLoad(Ad ad) {
      interstitial.show();
    }

    @Override
    public void onAdFailedToLoad(Ad ad) {
      Toast.makeText(this, getString(R.string.interstitial_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
      public void onAdClick(Ad ad) {
    }

    @Override
      public void onAdPresentScreen(Ad ad) {
    }

    @Override
      public void onAdDismissScreen(Ad ad) {
    }

    @Override
      public void onAdLeaveApplication(Ad ad) {
    }
    ```

1. Stop ad loading in activity onPause() method to avoid showing interstitial after leaving the activity.

    ```java
    @Override
    protected void onPause() {
      super.onPause();
      interstitial.stopLoading();
    }
    ```

1. Add below line to your activity config in AndroidManifest.xml to avoid reloading interstitial ad every time device orientation changes.

    ```xml
    android:configChanges="keyboard|keyboardHidden|orientation|uiMode|screenLayout|screenSize|smallestScreenSize"
    ```

See example code [here](Example/src/com/liquidm/sdk/example/FullscreenAdActivity.java) and [here](Example/AndroidManifest.xml) for more details.


## Request video ad in xml
1. Add liquidm namespace declaration to xml root element

    ```xml
    xmlns:liquidm="http://schemas.android.com/apk/lib/com.liquidm.sdk"
    ```

1. Add VideoAdView to your layout.
Remember to set your siteToken and videoPath.

    ```xml
    <!-- Replace TestTokn with your personal token. -->
    <!-- Enter videoPath to your video file. -->
    <com.liquidm.sdk.VideoAdView
        android:layout_width="200dp"
        android:layout_height="150dp"
        android:layout_gravity="center"
        liquidm:videoPath=""
        liquidm:siteToken="TestTokn" />
    ```

See [example layout](Example/src/com/liquidm/sdk/example/VideoAdFromCodeActivity.java) for more details.

See [Video ad fullscreen mode](#video-ad-fullscreen-mode) for fullscreen mode integration instructions.

## Request video ad in code

```java
// Replace TestTokn with your personal token.
String siteToken = "TestTokn";
 // Enter path to your video file here.
String videoPath = "";

videoAdView = new VideoAdView(this, siteToken, videoPath);

int width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics());
int height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, r.getDisplayMetrics());

FrameLayout mainLayout = (FrameLayout) findViewById(R.id.main_layout);
FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
mainLayout.addView(videoAdView, params);
```

See [example code](Example/res/layout/activity_video_ad_from_xml.xml) for more details.

See [Video ad fullscreen mode](#video-ad-fullscreen-mode) for fullscreen mode integration instructions.

# Request banner ad through DFP adapter

1. Integrate LiquidM SDK like described [above](#integrate-the-sdk-into-your-application)

1. Request AdMob banner ad like described [here](https://developers.google.com/mobile-ads-sdk/docs/admob/fundamentals) using AdMob *ad unit id* with configured LiquidM mediation.

# Request interstitial ad through DFP adapter

1. Integrate LiquidM SDK like described [above](#integrate-the-sdk-into-your-application)

1. Request AdMob interstitial ad like described [here](https://developers.google.com/mobile-ads-sdk/docs/admob/advanced) using AdMob *ad unit id* with configured LiquidM mediation.

# Other
## Video ad fullscreen mode
VideoAdView supports fullscreen mode, but part of its implementation have to be done by user.

Basic fullscreen mode implementation can be found in example in [VideoAdViewFullscreenModeProvider.java](Example/src/com/liquidm/sdk/example/VideoAdViewFullscreenModeProvider.java).

Follow below instructions to implement fullscreen mode the same way as it is implemented in example:

1. Copy [VideoAdViewFullscreenModeProvider.java](Example/src/com/liquidm/sdk/example/VideoAdViewFullscreenModeProvider.java) and its dependencies from example to your project.

1. Enable VideoAdView fullscreen button:

    ```java
    videoAdView.setFullscreenButtonVisible(true);
    ```

1. Create VideoAdViewFullscreenModeProvider:

    ```java
    videoAdViewFullscreenModeProvider = new VideoAdViewFullscreenModeProvider(activity, videoAdView, 16, 9);
    ```
16:9 is the aspect ratio that VideoAdViewFullscreenModeProvider will try to maintain while video player is in normal mode.

1. Invoke VideoAdViewFullscreenModeProvider.update():

    ```java
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        videoAdViewFullscreenModeProvider.update();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        videoAdViewFullscreenModeProvider.update();
    }
    ```

1. Implement VideoAdViewFullscreenModeProvider.Listener and hide/show remaining activity content when video player goes into fullscreen/normal mode:

    ```java
    public void onFullscreenChanged(boolean fullscreen) {
        if (fullscreen) {
            // hide content except VideoAdView
        } else {
           // show hidden content
        }
    }
    ```

1. Remember to hide title bar on older devices to avoid it in fullscreen mode.
Add the following code to Activity.onCreate():

    ```java
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        setContentView(R.layout.activity_video_ad_from_code);
        // ...
    }
    ```

See also [VideoAdFromCode.java](Example/src/com/liquidm/sdk/example/VideoAdFromCodeActivity.java)
or [VideoAdFromXml.java](Example/src/com/liquidm/sdk/example/VideoAdFromXmlActivity.java) for more details.

# Native ads [BETA]
Native ads allows you to customize ad rendering so it will fit the look and feel of your app.

Before you can request a native ad you have to choose a schema. The native ad schema determines what data is sent for the native ad, e.g. image , click URLs, etc. A simple example can look like the following:

```json
{
    "icon": "http://example.com/icon.png",
    "title": "Hello, world",
    "subtitle": "LiquidM"
}
```

You can access native ad data through ```NativeAd``` object and obtain its schema by using ```nativeAd.getSchema()```.

There are two ways to integrate native ads:
- you can prepare ad template and use ```NativeAdView``` to render it or
- download ```NativeAd``` manually using ```NativeAdProvider``` and render it by yourself.

## Render native ad using ```NativeAdView```

1. Create xml ad template and mark asset views with tags. For example: asset view for "image" asset should have tag = "image", like this:

    ```xml
    <ImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:tag="image" />
    ```
For real example, take a look [here](Example/res/layout/template_feed_ad.xml)

1. Create NativeAdView in xml in the following way. Remember to replace ```siteToken```, ```schemaName``` and ```@layout/template``` with your own values.

    ```xml
    <com.liquidm.sdk.NativeAdView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        liquidm:autoreload="true"
        liquidm:schemaName="feed_ad"
        liquidm:siteToken="TestTokn"
        liquidm:template="@layout/template" />
    ```
See [NativeAdFromXmlActivity.java](Example/src/com/liquidm/sdk/example/NativeAdFromXmlActivity.java) for more details.

1. ...or create NativeAdView in code in the following way. Remember to replace ```SITE_TOKEN```, ```SCHEMA_NAME```, ```TEMPLATE``` with your own values.

    ```java
    NativeAdView nativeAdView = new NativeAdView(this, SITE_TOKEN, SCHEMA_NAME, TEMPLATE);
    nativeAdView.setAutoreload(true);
    content.addView(nativeAdView);
    ```
See [NativeAdFromCodeActivity.java](Example/src/com/liquidm/sdk/example/NativeAdFromCodeActivity.java) for more details.

## Download NativeAd manually

1. Create ```NativeAdProvider```

    ```java
    NativeAdProvider nativeAdProvider = new NativeAdProvider(this, SITE_TOKEN, SCHEMA_NAME);
    ```

1. Attach ```NativeAdLoadListener``` to listen for new ads

    ```java
    nativeAdProvider.setListener(new NativeAdLoadListener() {
      @Override
      public void onAdLoad(NativeAd nativeAd) {
        // Render nativeAd manually
      }
      @Override
      public void onAdFailedToLoad() {
          // Handle loading failure
      }
    });
    ```

1. Load native ad

    ```java
    nativeAdProvider.loadAd();
    ```
or turn on autoreloading
    ```java
    nativeAdProvider.setAutoreload(true);
    ```

1. Use ```NativeAd``` methods to fetch asset data, for example:

    ```java
    String title = nativeAd.getString("title");
    int rating = nativeAd.getInt("rating");
    Bitmap image = nativeAd.getPreloadedImage("image");
    ```

## Other
### Customize ```NativeAdView``` template rendering
```NativeAdView``` is using ```NativeAdViewFiller``` for propagating template with native ad data. You can get it in the following way:

```java
NativeAdViewFiller nativeAdViewFiller = nativeAdView.getViewFiller();
```

```NativeAdViewFiller``` can fill ```TextView```, ```ImageView``` and ```Button``` views by default, but it can also be configured to fill custom views like this:

```java
nativeAdViewFiller.setAssetViewFiller(RatingBar.class, new NativeAdViewFiller.AssetViewFiller() {
    @Override
    public void fillViewWithNativeAdAssetData(View assetView, NativeAd nativeAd, String assetName) {
        RatingBar ratingBar = (RatingBar) assetView;
        ratingBar.setProgress(nativeAd.getInt(assetName));
    }
});
```

```NativeAdViewFiller``` searches for assets views by tags. It matches tag name with asset name by default. This too can be configured by writing custom ```AssetViewFnder```. Default implementation looks like this:

```java
nativeAdViewFiller.setAssetViewFinder(new NativeAdViewFiller.AssetViewFinder() {
	@Override
	public View findViewForAsset(String assetName, View nativeAdView) {
		return nativeAdView.findViewWithTag(assetName);
	}
});
```

```NativeAdViewFiller``` attaches click handler to all views by default. If you want to disable click handling on specified asset view then invoke:

```java
nativeAdViewFiller.setIgnoreAssetViewClicks(assetName, true);
```

### Native video interstitial ad
```NativeVideoInterstitialAd``` allows you to download and show native video interstitial ad. Please follow below steps to use it:

1. Create ```NativeVideoInterstitialAd```, set its listener, and load it.

    ```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      // ..

      // Replace TestTokn with your personal token.
      String siteToken = "TestTokn";
      interstitial = new NativeVideoInterstitialAd(this, "TestTokn");
      interstitial.setListener(this);

      interstitial.loadAd();
    }
    ```

1. Show interstitial ad in onAdLoad() event handler. Handle onAdFailedToLoad() event if needed.

    ```java
    @Override
    public void onAdLoad(NativeVideoInterstitialAd ad) {
      interstitial.show();
    }

    @Override
    public void onAdFailedToLoad(NativeVideoInterstitialAd ad) {
      Toast.makeText(this, getString(R.string.interstitial_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
      public void onAdClick(NativeVideoInterstitialAd ad) {
    }

    @Override
      public void onAdPresentScreen(NativeVideoInterstitialAd ad) {
    }

    @Override
      public void onAdDismissScreen(NativeVideoInterstitialAd ad) {
    }

    @Override
      public void onAdLeaveApplication(NativeVideoInterstitialAd ad) {
    }
    ```

1. Stop ad loading in activity onPause() method to avoid showing native video interstitial after leaving the activity.

    ```java
    @Override
    protected void onPause() {
      super.onPause();
      interstitial.stopLoading();
    }
    ```

See [NativeVideoInterstitialAdActivity.java](Example/src/com/liquidm/sdk/example/NativeVideoInterstitialAdActivity.java) for more details.

# FAQ
## How to select the best fitting banner size for most Android devices?
LiquidM banners sizes are measured in device-independent pixels. It means that for example 300x50 ad will look similarly on different devices, but will occupy different area measured in pixels.
On Android there are four categories of screen densities: ldpi, mdpi, hdpi, xhdpi and each one has its own scaling factor like on the image below:

![Android Screen Densities](http://developer.android.com/images/screens_support/screens-densities.png)

So a 300x50 (300dp x 50dp) ad will occupy:
- 225px x 37.5px on ldpi screen
- 300px x 50px on mdpi screen
- 450px x 75px on hdpi screen
- 600px x 100px on xhdpi screen

Having this in mind and knowing screen resolutions and densities of the devices that are the most important for you, you can determine the minimum screen size for every density category.

Having minimum screen sizes for every density category you can choose an ad size that will fit them all.

### Example
1. Let's focus on hdpi and xhdpi devices for simplicity and take the following ones into account:
  - HTC Desire Z: 480px x 800px hdpi
  - Samsung Galaxy S3 Mini: 480px x 800px hdpi
  - Samsung Galaxy S4 Mini: 540px x 960px hdpi
  - LG Nexus 4: 768px x 1280px xhdpi
  - Asus Nexus 7: 1200px x 1920px xhdpi
  - Samsung Galaxy Note: 800px x 1280px xhdpi

1. The smallest resolution in each group is:
  - 480px x 800px for hdpi
  - 768px x 1280px for xhdpi

1. The smallest resolution expressed in device-independent pixels and rounded down in each group is:
  - 320dp x 533dp for hdpi
  - 384dp x 640dp for xhdpi

1. And finally, the smallest resolution expressed in device-independent pixels is: 320dp x 533dp.

1. Summing up, 320x533 is the maximal ad size that will fit on all screens of above devices.

Having this in mind, you can choose to create 300x300 custom sized ad.

You will need 300px x 300px image for standard version and 600px x 600px image for hd version.
