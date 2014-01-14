package com.liquidm.sdk.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

}
