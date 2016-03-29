package com.getwandup.rxsensor.domain;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.Arrays;

/**
 * Container for SensorEvent
 *
 * @author manolovn
 */
public class RxSensorEvent {

    private float[] values;
    private Sensor sensor;
    private int accuracy;
    private long timestamp;

    public RxSensorEvent(float[] values, Sensor sensor, int accuracy, long timestamp) {
        this.values = values;
        this.sensor = sensor;
        this.accuracy = accuracy;
        this.timestamp = timestamp;
    }

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static RxSensorEvent from(SensorEvent sensorEvent) {
        return new RxSensorEvent(sensorEvent.values, sensorEvent.sensor, sensorEvent.accuracy,
                sensorEvent.timestamp);
    }

    @Override
    public String toString() {
        return "RxSensorEvent{"
                + "values=" + Arrays.toString(values)
                + ", sensor=" + sensor
                + ", accuracy=" + accuracy
                + ", timestamp=" + timestamp
                + '}';
    }
}
