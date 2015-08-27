/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kaeru
 */
public class ScheduleTest {
    private static int timer = 0;
    
    private static final ScheduledExecutorService scheduler =
       Executors.newScheduledThreadPool(1);

    public static void beepForAnHour() {
        final Runnable beeper = new Runnable() {
                public void run() { System.out.println((timer++)+" : beep"); }
            };
        
        final ScheduledFuture<?> beeperHandle =
            scheduler.scheduleAtFixedRate(beeper, 0, 1, TimeUnit.SECONDS);
        scheduler.schedule(new Runnable() {
                public void run() { beeperHandle.cancel(true); }
            }, 10, TimeUnit.SECONDS);
    }
    
    public static void main(String args[]){
        beepForAnHour();
    }

}
