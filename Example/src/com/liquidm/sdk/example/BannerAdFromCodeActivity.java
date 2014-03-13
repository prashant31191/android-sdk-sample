package com.liquidm.sdk.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import com.liquidm.sdk.AdSize;
import com.liquidm.sdk.AdView;

public class BannerAdFromCodeActivity extends Activity {

	// Enter your site token here
	private static final String SITE_TOKEN = "TestTokn";

	AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner_ad_from_code);

		adView = new AdView(this, SITE_TOKEN, AdSize.MMA);

		adView.setAutoreload(true);

		LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		layout.addView(adView, params);
	}

}
