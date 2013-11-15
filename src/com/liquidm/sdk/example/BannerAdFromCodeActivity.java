package com.liquidm.sdk.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.liquidm.sdk.AdSize;
import com.liquidm.sdk.AdView;

public class BannerAdFromCodeActivity extends Activity {

	AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner_ad_from_code);

		adView = new AdView(this, "TestTokn", AdSize.MMA);

		adView.setAutoreload(true);

		FrameLayout layout = (FrameLayout) findViewById(R.id.main_layout);
		layout.addView(adView, new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL));
	}

}
