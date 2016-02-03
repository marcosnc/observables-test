/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tests;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.observers.Observers;

public class SimpleTestCase {

    @Test
    public void usingObservables() {

        Observable<String> producer = Observable.create(consumer -> {
            consumer.onStart();
            consumer.onNext("One");
            consumer.onNext("Two");
            consumer.onNext("Three");
            consumer.onCompleted();
        });

        Observer<String> consumer = Observers.create(element -> System.out.println("An element: " + element));

        producer.subscribe(consumer);
    }

    @Test
    public void generatingEvents() throws InterruptedException {
        Observable<Long> producer = Observable.interval(1, TimeUnit.SECONDS);

        Observable<Long> limitedProducer = producer.take(5);
        Observable<String> textProducer = producer.map(i -> "A text: " + Long.toString(i));

        CountDownLatch latch = new CountDownLatch(1);

        Observer<Object> consumer = Observers.create(
                element ->  System.out.println("An element: " + element),
                error   ->  System.out.println("ERROR: "      + error.getMessage()),
                ()      -> {System.out.println("Completed.");
                            latch.countDown();}
        );


        limitedProducer.subscribe(consumer);
        textProducer.subscribe(consumer);

        latch.await();
    }

}
