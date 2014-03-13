package com.liquidm.sdk.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.liquidm.sdk.InterstitialAd;

public class FullscreenAdActivity extends Activity {

	// Enter your site token here
	private static final String SITE_TOKEN = "TestTokn";

	InterstitialAd interstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_ad);

		interstitial = new InterstitialAd(this, SITE_TOKEN);
	}

	public void onLoadClick(View view) {
		interstitial.loadAd();
	}

	public void onShowClick(View view) {
		if (interstitial.isReady()) {
			interstitial.show();
		} else if (!interstitial.isLoading()) {
			interstitial.loadAd();
		}
	}

}
