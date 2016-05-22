package com.getwandup.rxsensor.transformer;

import com.getwandup.rxsensor.domain.RxSensorEvent;

import org.junit.Test;

import java.util.Arrays;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test for {@link LowPassFilter}
 *
 * @author manolovn
 * @author TomeOkin
 */
public class LowPassFilterTest {

    @Test
    public void lowPassFilter_isCorrect() {
        TestSubscriber<float[]> subscriber = new TestSubscriber<>();
        RxSensorEvent[] events = new RxSensorEvent[]{
                new RxSensorEvent(new float[]{12, 11, 13}, null, 0, 0),
                new RxSensorEvent(new float[]{10, 10, 14}, null, 0, 0)
        };
        final float factor = 0.25f;
        float[] result = new float[]{
                12 + (10 - 12) * factor, 11 + (10 - 11) * factor, 13 + (14 - 13) * factor
        };

        Observable.from(events)
                .compose(new LowPassFilter(factor))
                .map(new Func1<RxSensorEvent, float[]>() {
                    @Override
                    public float[] call(RxSensorEvent rxSensorEvent) {
                        return rxSensorEvent.values;
                    }
                })
                .subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);

        assertArrayEquals(events[0].values, subscriber.getOnNextEvents().get(0), 0);
        assertArrayEquals(result, subscriber.getOnNextEvents().get(1), 0);
    }
}
