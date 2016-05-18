package com.getwandup.rxsensor.transformer;

import android.util.Log;

import com.getwandup.rxsensor.domain.RxSensorEvent;

import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Func1;

/**
 * Low pass filter implemented using Transformer api
 *
 * @author manolovn
 * @author TomeOkin
 * @see <a href="https://www.built.io/blog/2013/05/applying-low-pass-filter-to-android-sensors-readings/">Applying
 * Low Pass Filter to Android Sensor’s Readings</a>
 */
public class LowPassFilter implements Transformer<RxSensorEvent, RxSensorEvent> {
    private final float factor;
    private float[] sensorValue;

    public LowPassFilter(float factor) {
        this.factor = factor;
        Log.i("take", "create LowPassFilter");
    }

    @Override
    public Observable<RxSensorEvent> call(Observable<RxSensorEvent> source) {
        return source.map(new Func1<RxSensorEvent, RxSensorEvent>() {
            @Override
            public RxSensorEvent call(RxSensorEvent sensorEvent) {
                sensorValue = lowPass(sensorEvent.values.clone(), sensorValue);
                return sensorEvent;
            }
        });
    }

    protected float[] lowPass(float[] input, float[] output) {
        if (output == null) {
            return input;
        }

        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + factor * (input[i] - output[i]);
        }
        return output;
    }
}
