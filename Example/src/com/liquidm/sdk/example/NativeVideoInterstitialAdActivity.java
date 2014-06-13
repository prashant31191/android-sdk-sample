package com.liquidm.sdk.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.liquidm.sdk.NativeVideoInterstitialAd;

public class NativeVideoInterstitialAdActivity extends Activity implements NativeVideoInterstitialAd.Listener {

	// Enter your site token here
	private static final String SITE_TOKEN = "TestTokn";

	private NativeVideoInterstitialAd interstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_ad);

		interstitial = new NativeVideoInterstitialAd(this, SITE_TOKEN);
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
	public void onAdLoad(NativeVideoInterstitialAd ad) {
		interstitial.show();
	}

	@Override
	public void onAdLoadFailed(NativeVideoInterstitialAd ad) {
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

}
