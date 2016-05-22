package com.getwandup.rxsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.getwandup.rxsensor.domain.RxSensorEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Test for {@link RxSensor}
 *
 * @author manolovn
 */
public class RxSensorTest {

    private static final int ANY_SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;
    private static final int ANY_INVALID_SENSOR_TYPE = -1;
    private static final int ANY_VALID_SENSOR_TYPE = Sensor.TYPE_ACCELEROMETER;

    @Mock
    private SensorManager sensorManager;
    @Mock
    private Sensor sensor;
    @Mock
    private SensorEvent sensorEvent;

    private ArgumentCaptor<SensorEventListener> eventListenerArgumentCaptor =
            ArgumentCaptor.forClass(SensorEventListener.class);
    private TestSubscriber<RxSensorEvent> testSubscriber;
    private RxSensor rxSensor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFailOnInvalidSensorType() {
        givenATestSubscriber();
        givenARxSensor();

        whenRxSensorObserve(ANY_INVALID_SENSOR_TYPE, ANY_SENSOR_DELAY);

        testSubscriber.assertError(SensorNotFoundException.class);
    }

    @Test
    public void shouldNotFailOnValidSensorType() {
        givenATestSubscriber();
        givenARxSensor();
        givenASensorManager();

        whenRxSensorObserve(ANY_VALID_SENSOR_TYPE, ANY_SENSOR_DELAY);

        testSubscriber.assertNoErrors();
    }

    @Test
    public void shouldCallOnNextOnSensorChanged() {
        givenATestSubscriber();
        givenARxSensor();
        givenASensorManager();

        whenRxSensorObserve(ANY_VALID_SENSOR_TYPE, ANY_SENSOR_DELAY);

        testSubscriber.assertValueCount(1);
    }

    private void givenATestSubscriber() {
        testSubscriber = new TestSubscriber<>();
    }

    private void givenARxSensor() {
        Context context = mock(Context.class);
        when(context.getSystemService(Context.SENSOR_SERVICE)).thenReturn(sensorManager);
        rxSensor = new RxSensor(context);
    }

    private void givenASensorManager() {
        when(sensorManager.getDefaultSensor(anyInt())).thenReturn(sensor);
        when(sensorManager.registerListener(
                eventListenerArgumentCaptor.capture(), any(Sensor.class), anyInt()))
                .thenAnswer(new Answer<Void>() {
                    @Override
                    public Void answer(InvocationOnMock invocation) throws Throwable {
                        eventListenerArgumentCaptor.getValue().onSensorChanged(sensorEvent);
                        return null;
                    }
                });
    }

    private void whenRxSensorObserve(int sensorType, int sensorDelay) {
        Observable<RxSensorEvent> observable = rxSensor.observe(sensorType, sensorDelay);
        observable.subscribe(testSubscriber);
    }
}
