package catshakr.main;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

public class shakr {
	
	 private SensorManager mgr=null;
	 private long lastShakeTimestamp=0;
	 private double threshold=3000.0d;
	 private long gap=0;
	 private shakr.Callback cb=null;
	 
	 public shakr(Context ctxt, double threshold, long gap, shakr.Callback cb) {
		 this.threshold=threshold*threshold;
		 this.threshold=this.threshold
		 *SensorManager.GRAVITY_EARTH
		 *SensorManager.GRAVITY_EARTH;
		 this.gap=gap;
		 this.cb=cb;

		 mgr=(SensorManager)ctxt.getSystemService(Context.SENSOR_SERVICE);
		 mgr.registerListener(listener,
		 mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		 SensorManager.SENSOR_DELAY_UI);
	}

	 public void close() {
		 mgr.unregisterListener(listener);
	 }

	 private void isShaking() {
		 long now=SystemClock.uptimeMillis();

		 if (lastShakeTimestamp==0) {
			 lastShakeTimestamp=now;
		
			 if (cb!=null) {
			 cb.shakingStarted();
			 }
		 }
		 else {
			 lastShakeTimestamp=now;
		 }
	 }

	 private void isNotShaking() {
		 long now=SystemClock.uptimeMillis();

		 if (lastShakeTimestamp>0) {
			 if (now-lastShakeTimestamp>gap) {
				 lastShakeTimestamp=0;
		
				 if (cb!=null) {
					 cb.shakingStopped();
				 }
			 }
		 }
	 }

	 public interface Callback {
		 void shakingStarted();
		 void shakingStopped();
	 }

	 private SensorEventListener listener=new SensorEventListener() {
		 
		 public void onSensorChanged(SensorEvent e) {
			 if (e.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
				 double netForce=e.values[0]*e.values[0];
		
				 netForce+=e.values[1]*e.values[1];
				 netForce+=e.values[2]*e.values[2];
		
				 if (threshold<netForce) {
					 Log.w("ShakeListner","is shaking");
					 isShaking();
				 }
				 else {
					 isNotShaking();
				 }
			 }
		 }
	
		 public void onAccuracyChanged(Sensor sensor, int accuracy) {
			 // unused
		 }
	 };
}
