/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.observables;

import org.junit.Test;
import rx.Observable;
import rx.math.operators.OperatorSum;

public class Test_01_Simple {






    //----------------------------------------------------------------------
    //--- Showing the items ------------------------------------------------
    //----------------------------------------------------------------------

    @Test
    public void showingItems() {

        // Create an Observable to play with
        Observable<String> someObservable = receiveEvents();

        // Show the items, one per line
        someObservable.subscribe(
                (String item) -> { System.out.println(item); }
        );

    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------












    //----------------------------------------------------------------------
    //--- Simulating events ------------------------------------------------
    //----------------------------------------------------------------------

    private Observable<String> receiveEvents() {
        // That's how we create the observable
        return Observable.just("Mulesoft ", "Meetup ", "San ", "Diego ", "2016.\n");
    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------













    //----------------------------------------------------------------------
    //--- Transforming: MAP ------------------------------------------------
    //----------------------------------------------------------------------

    @Test
    public void transformMap() {

        Observable<String> words = receiveEvents();

        // Take the length for each word
        Observable<Integer> wordLength =
                words
                    .map(word -> word.length());
        wordLength.subscribe(System.out::println);


        System.out.println("==========");


        // Let's count all the letters
        Observable<Integer> totalLetters = OperatorSum.sumIntegers(wordLength);
        totalLetters.subscribe(System.out::println);
    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------














    //----------------------------------------------------------------------
    //--- Combining: ZIP ---------------------------------------------------
    //----------------------------------------------------------------------

    @Test
    public void combiningZip() {

        Observable<String>  words   = receiveEvents();
        Observable<Integer> lengths = words.map(word -> word.length());

        words
           .zipWith(lengths,
                    (word, count) -> String.format("%d: %s", count, word))
           .subscribe(System.out::println);

    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------
















    //----------------------------------------------------------------------
    //--- Selecting: SKIP and TAKE -----------------------------------------
    //----------------------------------------------------------------------

    @Test
    public void selectingSkipAndTake() {
        Observable<String> someObservable = receiveEvents();

        someObservable
                .subscribe(System.out::println);

        System.out.println("==========\n");

        someObservable
                .skip(1)
                .take(3)
                .subscribe(System.out::println);

    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------













    //----------------------------------------------------------------------
    //--- Selecting: FILTER ------------------------------------------------
    //----------------------------------------------------------------------

    @Test
    public void selectingFilter() {
        Observable<String> someObservable = receiveEvents();

        someObservable
                .subscribe(System.out::println);

        System.out.println("==========\n");

        someObservable
                .filter(item -> item.startsWith("M"))
                .subscribe(System.out::println);

    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------









}
