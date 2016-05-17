package com.getwandup.rxsensor.domain;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.Arrays;

/**
 * Container for SensorEvent
 *
 * @author manolovn
 * @author TomeOkin
 */
public class RxSensorEvent {
    public float[] values;
    public Sensor sensor;
    public int accuracy;
    public long timestamp;

    public RxSensorEvent(float[] values, Sensor sensor, int accuracy, long timestamp) {
        this.values = values;
        this.sensor = sensor;
        this.accuracy = accuracy;
        this.timestamp = timestamp;
    }

    public static RxSensorEvent from(SensorEvent sensorEvent) {
        return new RxSensorEvent(sensorEvent.values, sensorEvent.sensor, sensorEvent.accuracy,
                sensorEvent.timestamp);
    }

    @Override
    public String toString() {
        return "RxSensorEvent{"
                + "values="
                + Arrays.toString(values)
                + ", sensor="
                + sensor
                + ", accuracy="
                + accuracy
                + ", timestamp="
                + timestamp
                + '}';
    }
}
