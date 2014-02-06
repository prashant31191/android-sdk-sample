## Project Integration
### Copy liquidmsdk.jar into libs/ directory
### Set required permissions in AndroidManifest.xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

### Set optional permissions in AndroidManifest.xml
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

### Define AdActivity in AndroidManifest.xml

    <activity
      android:name="com.liquidm.sdk.AdActivity"
      android:configChanges="keyboard|keyboardHidden|orientation|uiMode|screenLayout|screenSize|smallestScreenSize" />

## Create banner in xml
### Add liquidm namespace declaration to xml root element 
    xmlns:liquidm="http://schemas.android.com/apk/lib/com.liquidm.sdk"

### Add AdView to your layout.
Remember to set your siteToken.

    <com.liquidm.sdk.AdView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center|bottom"
      liquidm:adSize="mma"
      liquidm:autoreload="true"
      liquidm:siteToken="" />

See [example layout](https://github.com/madvertise/android-sdk-sample/blob/master/res/layout/activity_banner_ad_from_xml.xml) for more details.

## Create banner in code
    String siteToken = ""; // Enter here your site token.
    AdView adView = new AdView(this, siteToken, AdSize.MMA);

    adView.setAutoreload(true);

    layout.addView(adView, new FrameLayout.LayoutParams(
      LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
      Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL));

## Create interstitial
### Create and load InterstitialAd
    String siteToken = ""; // Enter here your site token.
    InterstitialAd interstitialAd = new InterstitialAd(this, siteToken);
    interstitialAd.loadAd();


### Show interstitial and reload
    if (interstitial.isReady()) {
      interstitial.show();
    } else if (!interstitial.isLoading()) {
      interstitial.loadAd();
    }

## Create video ad in xml
### Add liquidm namespace declaration to xml root element
    xmlns:liquidm="http://schemas.android.com/apk/lib/com.liquidm.sdk"

### Add VideoAdView to your layout.
Remember to set your siteToken and videoPath.

    <!-- Enter your site token and video path below -->
    <com.liquidm.sdk.VideoAdView
        android:layout_width="200dp"
        android:layout_height="150dp"
        android:layout_gravity="center"
        liquidm:videoPath=""
        liquidm:siteToken="" />

See [example layout](https://github.com/madvertise/android-sdk-sample/blob/master/res/layout/activity_video_ad_from_xml.xml) for more details.

See [Video ad fullscreen mode](#video-ad-fullscreen-mode) for fullscreen mode integration instructions.

## Create video ad in code
    String siteToken = ""; // Enter here your site token.
    String videoPath = ""; // Enter path to your video here.

    videoAdView = new VideoAdView(this, siteToken, videoPath);

    int width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics());
    int height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, r.getDisplayMetrics());
    
    FrameLayout mainLayout = (FrameLayout) findViewById(R.id.main_layout);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
    mainLayout.addView(videoAdView, params);

See [Video ad fullscreen mode](#video-ad-fullscreen-mode) for fullscreen mode integration instructions.

## Video ad fullscreen mode
VideoAdView supports fullscreen mode, but part of its implementation have to be done by user.

Basic fullscreen mode implementation can be found in example in [VideoAdViewFullscreenModeProvider.java](https://github.com/madvertise/android-sdk-sample/blob/master/src/com/liquidm/sdk/example/VideoAdViewFullscreenModeProvider.java).

Follow below instructions to implement fullscreen mode the same way as it is implemented in example:

1. Copy [VideoAdViewFullscreenModeProvider.java](https://github.com/madvertise/android-sdk-sample/blob/master/src/com/liquidm/sdk/example/VideoAdViewFullscreenModeProvider.java) and its dependencies from example to your project.

1. Enable VideoAdView fullscreen button:

        videoAdView.setFullscreenButtonVisible(true);

1. Create VideoAdViewFullscreenModeProvider:

        videoAdViewFullscreenModeProvider = new VideoAdViewFullscreenModeProvider(activity, videoAdView, 16, 9);
16:9 is the aspect ratio that VideoAdViewFullscreenModeProvider will try to maintain while video player is in normal mode.

1. Invoke VideoAdViewFullscreenModeProvider.update():

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

1. Implement VideoAdViewFullscreenModeProvider.Listener and hide/show remaining activity content when video player goes into fullscreen/normal mode:

        public void onFullscreenChanged(boolean fullscreen) {
            if (fullscreen) {
                // hide content except VideoAdView
            } else {
               // show hidden content
            }
        }

1. Remember to hide title bar on older devices to avoid it in fullscreen mode.
Add the following code to Activity.onCreate():

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
        
            setContentView(R.layout.activity_video_ad_from_code);
            // ...
        }

See also [VideoAdFromCode.java](https://github.com/madvertise/android-sdk-sample/blob/master/src/com/liquidm/sdk/example/VideoAdFromCodeActivity.java)
or [VideoAdFromXml.java](https://github.com/madvertise/android-sdk-sample/blob/master/src/com/liquidm/sdk/example/VideoAdFromXmlActivity.java) for more details.

## Example
See [LiquidM SDK Example](https://github.com/madvertise/android-sdk-sample/) to learn more about LiquidM SDK integration. (Remember to enter your own site token in marked places.)