package com.liquidm.sdk.example;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.liquidm.sdk.VideoAdView;

public class VideoAdFromXmlActivity extends Activity implements VideoAdViewFullscreenModeProvider.Listener {

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

		setContentView(R.layout.activity_video_ad_from_xml);

		videoAdView = (VideoAdView) findViewById(R.id.video_ad_view);
		videoAdView.setFullscreenButtonVisible(true);
		videoAdViewFullscreenModeProvider = new VideoAdViewFullscreenModeProvider(this, videoAdView, 16, 9);
		videoAdViewFullscreenModeProvider.setListener(this);

		contentView = findViewById(R.id.content_view);
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
