/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tests;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ObservablesTestCase {

    @Test
    public void creation() {
        final Random randomGenerator = new Random();

        Action1 showSomething = (i) -> System.out.println(i);

        Observable<Long> eventsGenerator =
                Observable
                        .interval(1, TimeUnit.SECONDS)
                        .map(i -> {
                            System.out.printf("ORIGINAL: %d on Thread %d\n", i, Thread.currentThread().getId());
                            return i;
                        });



        //eventsGenerator = eventsGenerator.subscribeOn(Schedulers.newThread());
        Observable<String> eventsFromA = eventsGenerator.observeOn(Schedulers.newThread()).map(i -> String.format("A -> %d (on thread %d)", i, Thread.currentThread().getId()));
        Observable<String> eventsFromB = eventsGenerator.observeOn(Schedulers.newThread()).map(i -> String.format("B -> %d (on thread %d)", i, Thread.currentThread().getId()));

        //Observable<String> eventsFromA = eventsGenerator.map(i -> String.format("A -> %d (on thread %d)", i, Thread.currentThread().getId()));
        //Observable<String> eventsFromB = eventsGenerator.map(i -> String.format("B -> %d (on thread %d)", i, Thread.currentThread().getId()));

        eventsFromA.subscribe(showSomething);
        eventsFromB.subscribe(showSomething);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void anotherCreation() {
        Observable<String> observable = Observable.create(subscriber -> {
            System.out.println("Creating the Observable...");
            System.out.println("Calling start");
            subscriber.onStart();
            System.out.println("Calling next");
            subscriber.onNext("Hi");
            System.out.println("Calling completed");
            subscriber.onCompleted();
            System.out.println("Finished");
        });

        System.out.println("Before subscription A");
        observable.subscribe(i -> {
            System.out.println(i);
        });
        System.out.println("After subscription A");

        System.out.println("Before subscription B");
        observable.subscribe(i -> {
            System.out.println(i);
        });
        System.out.println("After subscription B");

    }
}
