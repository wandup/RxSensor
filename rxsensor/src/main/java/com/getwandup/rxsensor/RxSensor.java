package com.getwandup.rxsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.getwandup.rxsensor.domain.RxSensorEvent;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * RxSensor
 *
 * @author manolovn
 */
public class RxSensor {

    private final SensorManager sensorManager;

    public RxSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public Observable<RxSensorEvent> observe(final int sensorType, final int samplingPeriodUs) {
        return Observable.create(new Observable.OnSubscribe<RxSensorEvent>() {

            @Override
            public void call(final Subscriber<? super RxSensorEvent> subscriber) {

                final Sensor sensor = sensorManager.getDefaultSensor(sensorType);

                if (sensor == null) {
                    subscriber.onError(new SensorNotFoundException());
                }

                final SensorEventListener sensorEventListener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        subscriber.onNext(RxSensorEvent.from(event));
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                };

                sensorManager.registerListener(sensorEventListener, sensor, samplingPeriodUs);

                subscriber.add(new Subscription() {

                    private boolean isUnsubscribed = false;

                    @Override
                    public void unsubscribe() {
                        sensorManager.unregisterListener(sensorEventListener);
                        isUnsubscribed = true;
                    }

                    @Override
                    public boolean isUnsubscribed() {
                        return isUnsubscribed;
                    }
                });
            }
        });
    }
}
