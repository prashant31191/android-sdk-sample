package com.liquidm.sdk.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.liquidm.sdk.Ad;
import com.liquidm.sdk.AdListener;
import com.liquidm.sdk.InterstitialAd;

public class FullscreenAdActivity extends Activity implements AdListener {

	// Enter your site token here
	private static final String SITE_TOKEN = "TestTokn";

	InterstitialAd interstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_ad);

		interstitial = new InterstitialAd(this, SITE_TOKEN);
		interstitial.setListener(this);

		interstitial.loadAd();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		interstitial.stopLoading();
	}

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

}
