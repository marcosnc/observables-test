/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.observables;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import rx.Observable;

public class Test_03_Creating {

    @Test
    public void createObservableManually() {
        randomSequence(100).subscribe(System.out::println);
    }

    @Test
    public void simulateEvents() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Random rnd = new Random();

        //randomSequence(10)
        Observable.range(1, 50)
                .delay(item -> Observable.just(item).delay(rnd.nextInt(5), TimeUnit.SECONDS))
                .subscribe(System.out::println, Throwable::printStackTrace, () -> latch.countDown());

        latch.await();
    }


    private Observable<Integer> randomSequence(int itemsCount) {
        return Observable.create(subscriber -> {
            Random random = new Random();
            subscriber.onStart();
            for(int i=0; i<itemsCount; i++) {
                subscriber.onNext(random.nextInt(10000));
            }
            subscriber.onCompleted();
        });
    }

}

