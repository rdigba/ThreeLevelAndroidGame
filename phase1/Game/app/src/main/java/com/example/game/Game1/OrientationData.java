package com.example.game.Game1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * OrientationData class.
 */
public class OrientationData implements SensorEventListener {

    /**
     * Instance variables.
     */
    private float[] accelOutput;
    private float[] magOutput;
    private float[] orientation = new float[3];
    private float[] startOrientation = null;

    /**
     * Constructor
     */
    OrientationData() {
        SensorManager manager = (SensorManager) Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        assert manager != null;
    }

    /**
     * @return - orientation
     */
    float[] getOrientation() {
        return orientation;
    }

    /**
     * @return - startOrientation
     */
    float[] getStartOrientation() {
        return startOrientation;
    }

    /**
     * starts newGame
     */
    void newGame() {
        startOrientation = null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelOutput = event.values;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magOutput = event.values;
        }
        if (accelOutput != null && magOutput != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput);
            if (success) {
                SensorManager.getOrientation(R, orientation);
                if (startOrientation == null) {
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                }
            }
        }
    }

    /*
     public void register() {
     manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
     manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
     }

     public void pause() {
     manager.unregisterListener(this);
     }
     */
}
