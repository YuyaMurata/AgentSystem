/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.run;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author kaeru
 */
public class TestMain {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        
        
        Runnable r1 = new RunnableTimeTest();
        Runnable r2 = new RunnableTimeTest();
        FutureTest.timedRun(r1, 10, TimeUnit.SECONDS);
        FutureTest.timedRun(r2, 10, TimeUnit.SECONDS);
        
        long stop = System.currentTimeMillis();
        System.out.println("ExecTime : "+(stop-start));
    }
}
