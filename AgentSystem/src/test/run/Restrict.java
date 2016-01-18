/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.run;

import rda.timer.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaeru
 */
public class Restrict implements Runnable{
    private Runnable r;
    private long timeout, delay;
    private TimeUnit unit;
    
    private Restrict(Runnable r, long delay, long timeout, TimeUnit unit){
        this.r = r;
        this.delay = delay;
        this.timeout = timeout+delay;
        this.unit = unit;
    }
    
    private static ExecutorService exec;
    public static void timedRun(Runnable r, long delay, long timeout, TimeUnit unit){
        exec = Executors.newSingleThreadExecutor();
        exec.execute(new Restrict(r, delay, timeout, unit));
        exec.shutdown();
    }
    
    private static TimeUnit _scheduleUnit = TimeUnit.SECONDS;
    private static Long _period = 1L;
    public static void setRestrictParam(Long period, TimeUnit unit){
        _period = period;
        _scheduleUnit = unit;
    }

    @Override
    public void run() {
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        Future f = schedule.scheduleAtFixedRate(r, delay, _period, _scheduleUnit);
        try{
            f.get(timeout, unit);
        } catch(TimeoutException e1) {
        } catch (InterruptedException e2) {
        } catch (ExecutionException e3) {
        } finally {
            f.cancel(true);
            schedule.shutdown();
        }
    }
    
    public static void syncStop(){
        while(!exec.isTerminated()) ;
    }
}
