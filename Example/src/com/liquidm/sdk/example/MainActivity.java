package com.liquidm.sdk.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.liquidm.sdk.Version;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			onAboutButtonClick(null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onAboutButtonClick(View view) {
		String message = "LiquidM SDK version:\n" + Version.FULL_NAME;

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle(R.string.app_name);
		dialogBuilder.setMessage(message);
		dialogBuilder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = dialogBuilder.create();
		dialog.show();
	}

	public void onShowBannerAdXMLClick(View view) {
		Intent i = new Intent(this, BannerAdFromXmlActivity.class);
		startActivity(i);
	}

	public void onShowBannerAdCodeClick(View view) {
		Intent i = new Intent(this, BannerAdFromCodeActivity.class);
		startActivity(i);
	}

	public void onShowFullscreenAdClick(View view) {
		Intent i = new Intent(this, FullscreenAdActivity.class);
		startActivity(i);
	}

	public void onShowVideoAdXMLClick(View view) {
		Intent i = new Intent(this, VideoAdFromXmlActivity.class);
		startActivity(i);
	}

	public void onShowVideoAdCodeClick(View view) {
		Intent i = new Intent(this, VideoAdFromCodeActivity.class);
		startActivity(i);
	}

	public void onShowNativeAdXmlClick(View view) {
		Intent i = new Intent(this, NativeAdFromXmlActivity.class);
		startActivity(i);
	}

	public void onShowNativeAdCodeClick(View view) {
		Intent i = new Intent(this, NativeAdFromCodeActivity.class);
		startActivity(i);
	}

	public void onShowNativeVideoInterstitialAdClick(View view) {
		Intent i = new Intent(this, NativeVideoInterstitialAdActivity.class);
		startActivity(i);
	}

}
