package com.liquidm.sdk.example;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ActivityOrientationLocker implements SensorEventListener {

	private Activity activity;
	private SensorManager sensorManager;

	private int lockedOrientation;
	private boolean listening;

	public ActivityOrientationLocker(Activity activity) {
		this.activity = activity;
		this.sensorManager = (SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);
	}

	public void lockOrientation(int orientation) {
		lockedOrientation = orientation;
		activity.setRequestedOrientation(orientation);

		if (!listening) {
			sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_UI);
			listening = true;
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float gx = event.values[0];
		float gy = event.values[1];

		boolean vertical = (Math.abs(gy) > 4.0f);
		boolean horizontal = (Math.abs(gx) > 4.0f);

		int orientation;
		if (vertical && !horizontal) {
			orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
		} else if (horizontal && !vertical) {
			orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
		} else {
			orientation = -1;
		}

		if (listening) {
			if (orientation == lockedOrientation) {
				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
				sensorManager.unregisterListener(this);
				listening = false;
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}
