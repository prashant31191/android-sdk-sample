package com.liquidm.sdk.example;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;

import com.liquidm.sdk.VideoAdView;
import com.liquidm.sdk.example.VideoAdViewFullscreenModeProvider.Listener;

public class VideoAdFromCodeActivity extends Activity implements Listener {

	// Enter your site token and video path here
	private static final String SITE_TOKEN = "TestTokn";
	private static final String VIDEO_PATH = "http://madvertise-assets-production.s3.amazonaws.com/assets/video-tests/poh.mp4";

	private VideoAdView videoAdView;
	private VideoAdViewFullscreenModeProvider videoAdViewFullscreenModeProvider;

	private View contentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide title bar on older phones so it won't be visible in fullscreen mode
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		}

		setContentView(R.layout.activity_video_ad_from_code);
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
		contentView = findViewById(R.id.content_view);

		videoAdView = new VideoAdView(this, SITE_TOKEN, VIDEO_PATH);
		videoAdView.setFullscreenButtonVisible(true);
		videoAdViewFullscreenModeProvider = new VideoAdViewFullscreenModeProvider(this, videoAdView, 16, 9);
		videoAdViewFullscreenModeProvider.setListener(this);

		{
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			mainLayout.addView(videoAdView, 0, params);
		}
	}

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

	@Override
	public void onFullscreenChanged(boolean fullscreen) {
		contentView.setVisibility(fullscreen ? View.GONE : View.VISIBLE);
	}

}
