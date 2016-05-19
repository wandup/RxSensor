package com.getwandup.rxsensor;

import com.getwandup.rxsensor.domain.RxSensorEvent;
import com.getwandup.rxsensor.transformer.LowPassFilter;

import org.junit.Test;

import java.util.Arrays;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void lowPassFilter_isCorrect() {
        TestSubscriber<float[]> subscriber = new TestSubscriber<>();
        RxSensorEvent[] events = new RxSensorEvent[] {
                new RxSensorEvent(new float[] {12, 11, 13}, null, 0, 0),
                new RxSensorEvent(new float[] {10, 10, 14}, null, 0, 0)
        };
        final float factor = 0.25f;
        float[] result = new float[] {
                12 + (10 - 12) * factor, 11 + (10 - 11) * factor, 13 + (14 - 13) * factor
        };

        Observable.from(events)
                .compose(new LowPassFilter(factor))
                .map(new Func1<RxSensorEvent, float[]>() {
                    @Override
                    public float[] call(RxSensorEvent rxSensorEvent) {
                        System.out.println(Arrays.toString(rxSensorEvent.values));
                        return rxSensorEvent.values;
                    }
                })
                .subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);

        System.out.println("result: " + Arrays.toString(result));
        assertArrayEquals(events[0].values, subscriber.getOnNextEvents().get(0), 0);
        assertArrayEquals(result, subscriber.getOnNextEvents().get(1), 0);
        //subscriber.assertValues(events[0].values, result);
    }
}
