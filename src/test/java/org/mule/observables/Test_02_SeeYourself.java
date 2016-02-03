/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.observables;

import java.lang.reflect.Method;

import org.junit.Test;
import rx.Observable;

public class Test_02_SeeYourself {

    @Test
    public void inspectObservablesApi() {
        System.out.println("\n\nRxJava operators:");

        Observable.from(Observable.class.getMethods())
                .filter(m -> Observable.class.isAssignableFrom(m.getReturnType()))
                .map(Method::getName)
                .distinct()
                .toSortedList()
                .forEach(System.out::println);
                //.subscribe(list -> list.forEach(System.out::println));
    }
}
