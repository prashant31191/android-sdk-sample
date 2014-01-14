package com.liquidm.sdk.example;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.liquidm.sdk.VideoAdView;

public class VideoAdViewFullscreenModeProvider implements VideoAdView.Listener {

	public interface Listener {
		void onFullscreenChanged(boolean fullscreen);
	}

	private Activity activity;
	private VideoAdView videoAdView;
	private ActivityOrientationLocker orientationLocker;
	private int aspectRatioW;
	private int aspectRatioH;
	private Listener listener;

	public VideoAdViewFullscreenModeProvider(Activity activity, VideoAdView videoAdView, int aspectRatioW,
			int aspectRatioH) {
		this.activity = activity;
		this.videoAdView = videoAdView;
		this.orientationLocker = new ActivityOrientationLocker(activity);
		this.aspectRatioW = aspectRatioW;
		this.aspectRatioH = aspectRatioH;

		this.videoAdView.setListener(this);
	}

	public void update() {
		Configuration configuration = activity.getResources().getConfiguration();

		if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			videoAdView.setFullscreenMode(true);

			if (listener != null) {
				listener.onFullscreenChanged(true);
			}

			resizeVideoView(true);

			setActivityFullscreenMode(activity, true);
		} else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (listener != null) {
				listener.onFullscreenChanged(false);
			}

			resizeVideoView(false);

			setActivityFullscreenMode(activity, false);

			videoAdView.setFullscreenMode(false);
		}
	}

	@Override
	public void onVideoAdFullscreenModeChanged(VideoAdView videoAdView, boolean fullscreenMode, boolean fromUser) {
		if (fromUser) {
			orientationLocker.lockOrientation(fullscreenMode ? ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
					: ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
	}

	@Override
	public void onVideoAdControlsVisibilityChanged(VideoAdView videoAdView, boolean visible) {
		if (videoAdView.isFullscreenMode()) {
			setSoftwareButtonsVisibility(activity, visible);
		}
	}

	private void resizeVideoView(boolean fullscreen) {
		LayoutParams params = videoAdView.getLayoutParams();
		if (fullscreen) {
			params.width = LayoutParams.MATCH_PARENT;
			params.height = LayoutParams.MATCH_PARENT;
		} else {
			// maintain aspect ratio
			params.width = activity.getResources().getDisplayMetrics().widthPixels;
			params.height = params.width * aspectRatioH / aspectRatioW;
		}
	}

	private void setActivityFullscreenMode(Activity activity, boolean fullscreen) {
		setStatusBarVisibility(activity, !fullscreen);
		setSoftwareButtonsVisibility(activity, !fullscreen);
		setActionBarVisibility(activity, !fullscreen);
	}

	private void setStatusBarVisibility(Activity activity, boolean visible) {
		WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();

		if (visible) {
			attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		} else {
			attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		}

		activity.getWindow().setAttributes(attrs);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void setSoftwareButtonsVisibility(Activity activity, boolean visible) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			int flag = (visible ? View.SYSTEM_UI_FLAG_VISIBLE : View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

			activity.getWindow().getDecorView().setSystemUiVisibility(flag);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setActionBarVisibility(Activity activity, boolean visible) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			if (visible) {
				activity.getActionBar().show();
			} else {
				activity.getActionBar().hide();
			}
		}
	}

	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

}
