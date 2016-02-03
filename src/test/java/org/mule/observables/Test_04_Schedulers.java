/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.observables;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.internal.schedulers.NewThreadWorker;
import rx.internal.util.RxThreadFactory;

public class Test_04_Schedulers {





    //----------------------------------------------------------------------
    //--- Schedulers -------------------------------------------------------
    //----------------------------------------------------------------------

    @Test
    public void schedulers() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Observable
                .just("ONE ITEM")
                .map(this::log)
                .observeOn(new TestScheduler("SCHEDULER-A-"))
                .map(this::log)
                .observeOn(new TestScheduler("SCHEDULER-B-"))
                .map(this::log)
                .observeOn(new TestScheduler("SCHEDULER-C-"))
                .map(this::log)
                //.subscribeOn(new TestScheduler("SCHEDULER-X-"))
                .map(this::log)
                .subscribe(item -> latch.countDown());

        latch.await();
    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------


    private String log(String item) {
        System.out.println(Thread.currentThread().getName() + "   "  + item);
        return item;
    }


    //----------------------------------------------------------------------
    //----------------------------------------------------------------------


    class TestScheduler extends Scheduler {

        private final RxThreadFactory threadFactory;

        private TestScheduler(String prefix) {
            threadFactory = new RxThreadFactory(prefix);
        }

        @Override
        public Worker createWorker() {
            return new NewThreadWorker(threadFactory);
        }
    }



}

