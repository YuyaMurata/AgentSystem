/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.run;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaeru
 */
public class TestMain {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        
        
        Runnable r1 = new RunnableTimeTest();
        Runnable r2 = new RunnableTimeTest();
        Restrict rest1 = new Restrict();
        rest1.setRestrictParam(1000L, TimeUnit.MILLISECONDS);
        rest1.timedRun(r1, 0, 10, TimeUnit.SECONDS);
        
        //Restrict rest2 = new Restrict();
        //rest2.setRestrictParam(1000L, TimeUnit.MILLISECONDS);
        //rest2.timedRun(r2, 5, 10, TimeUnit.SECONDS);
        
        rest1.syncStop();
        
        long stop = System.currentTimeMillis();
        System.out.println("ExecTime : "+(stop-start));
    }
}
