package com.getwandup.rxsensorsample;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.getwandup.rxsensor.RxSensor;
import com.getwandup.rxsensor.domain.RxSensorEvent;
import com.getwandup.rxsensor.transformer.LowPassFilter;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxSensor rxSensor = new RxSensor(this);
        rxSensor.observe(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL)
                .compose(new LowPassFilter(0.5f))
                .subscribe(new Subscriber<RxSensorEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RxSensorEvent sensorEvent) {
                        Log.d("RxSensor", "values: " + sensorEvent.toString());
                    }
                });
    }
}
