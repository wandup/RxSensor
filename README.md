# RxSensor
[![Build Status](https://travis-ci.org/wandup/RxSensor.svg?branch=master)](https://travis-ci.org/wandup/RxSensor)
[![codecov](https://codecov.io/gh/wandup/RxSensor/branch/master/graph/badge.svg)](https://codecov.io/gh/wandup/RxSensor)
[![Maven Central](https://img.shields.io/maven-central/v/com.getwandup/rxsensor.svg)](https://maven-badges.herokuapp.com/maven-central/com.getwandup/rxsensor)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxSensor-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3350)

Simple Reactive wrapper for Android sensors 

# Usage

Add the dependency

```groovy
dependencies {
    compile 'com.getwandup:rxsensor:1.0.0'
}
```

Just init and create observer:

```java
RxSensor rxSensor = new RxSensor(this);
rxSensor.observe(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL)
    .subscribe(new Subscriber<RxSensorEvent>() {
        @Override
        public void onCompleted() { }
        
        @Override
        public void onError(Throwable e) { }
        
        @Override
        public void onNext(RxSensorEvent sensorEvent) {
            Log.d("RxSensor", "event: " + sensorEvent.toString());
        }
    });
```

# License

    Copyright 2016 Manuel Vera Nieto
    Copyright 2016 TomeOkin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
