/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.run;

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
public class FutureTest implements Runnable{
    private Runnable r;
    private long timeout;
    private TimeUnit unit;
    
    private FutureTest(Runnable r, long timeout, TimeUnit unit){
        this.r = r;
        this.timeout = timeout;
        this.unit = unit;
    }
    
    private static ExecutorService exec;
    public static void timedRun(Runnable r, long timeout, TimeUnit unit){
        exec = Executors.newSingleThreadExecutor();
        exec.execute(new FutureTest(r, timeout, unit));
        exec.shutdown();
    }
    
    private static TimeUnit _scheduleUnit = TimeUnit.SECONDS;
    private static Long _period = 1L;
    public static void setScheduleParam(Long period, TimeUnit unit){
        _period = period;
        _scheduleUnit = unit;
    }

    @Override
    public void run() {
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        Future f = schedule.scheduleAtFixedRate(r, 0, _period, _scheduleUnit);
        try{
            f.get(timeout, unit);
        } catch(TimeoutException e) {
        } catch (InterruptedException ex) {
        } catch (ExecutionException ex) {
        } finally {
            f.cancel(true);
            schedule.shutdown();
        }
    }
    
    public static void syncStop(){
        while(!exec.isTerminated()) ;
    }
}
