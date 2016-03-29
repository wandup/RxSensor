package com.getwandup.rxsensor.transformer;

import com.getwandup.rxsensor.domain.RxSensorEvent;

import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Func1;

/**
 * Low pass filter implemented using Transformer api
 *
 * @author manolovn
 */
public class LowPassFilter implements Transformer<RxSensorEvent, RxSensorEvent> {

    private final float factor;

    public LowPassFilter(float factor) {
        this.factor = factor;
    }

    @Override
    public Observable<RxSensorEvent> call(Observable<RxSensorEvent> source) {
        return source.map(new Func1<RxSensorEvent, RxSensorEvent>() {
            @Override
            public RxSensorEvent call(RxSensorEvent sensorEvent) {
                sensorEvent.setValues(
                        lowPass(sensorEvent.getValues().clone(), sensorEvent.getValues()));
                return sensorEvent;
            }
        });
    }

    protected float[] lowPass(float[] input, float[] output) {
        if (output == null) return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + factor * (input[i] - output[i]);
        }
        return output;
    }
}
