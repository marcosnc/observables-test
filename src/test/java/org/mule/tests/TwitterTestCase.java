/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tests;

import org.junit.Test;
import rx.Observable;

public class TwitterTestCase {

    @Test
    public void mainWork() {

        retrieveListFromSalesForce()
                .flatMap(client -> checkFollowing(client))
                .flatMap(client -> sendMessage(client))
                .flatMap(client -> updateSalesForce(client))
                .subscribe();
    }


    private Observable<SalesForceClient> retrieveListFromSalesForce() {
        return Observable.empty();
    }

    private Observable<ExtendedSalesForceClient> checkFollowing(SalesForceClient salesForceClient) {
        return Observable.empty();
    }


    private Observable<ExtendedSalesForceClient> sendMessage(ExtendedSalesForceClient extendedSalesForceClient) {
        return Observable.empty();
    }

    private Observable<ExtendedSalesForceClient> updateSalesForce(ExtendedSalesForceClient client) {
        return Observable.empty();
    }


    private class SalesForceClient {  }

    private class ExtendedSalesForceClient { }
}
