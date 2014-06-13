package com.liquidm.sdk.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.liquidm.sdk.NativeAdView;

public class NativeAdFromCodeActivity extends Activity {

	// Enter your site token and schema name here
	private static final String SITE_TOKEN = "TestTokn";
	private static final String SCHEMA_NAME = "feed_ad";
	private static final int TEMPLATE = R.layout.template_feed_ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_native_ad_from_code);

		FrameLayout content = (FrameLayout) findViewById(R.id.content);

		NativeAdView nativeAdView = new NativeAdView(this, SITE_TOKEN, SCHEMA_NAME, TEMPLATE);
		nativeAdView.setAutoreload(true);
		content.addView(nativeAdView);
	}

}
