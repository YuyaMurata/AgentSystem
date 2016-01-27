/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.timer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author kaeru
 */
public class Restrict implements Runnable{
    private static Integer cnt = 0;
    private Runnable r;
    private long timeout, delay;
    private TimeUnit unit;
    
    private final ExecutorService exec = Executors.newSingleThreadExecutor(new ThreadFactory()
    {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Restrict:"+cnt++);
        }
    });
    
    public Restrict() {
    }
    
    private Restrict(Runnable r, long delay, long timeout, TimeUnit unit){
        this.r = r;
        this.delay = delay;
        this.timeout = timeout+delay;
        this.unit = unit;
    }
    
    public void timedRun(Runnable r, long delay, long timeout, TimeUnit unit){
        Restrict rest = new Restrict(r, delay, timeout, unit);
        rest.start(rest);
    }
    
    private TimeUnit scheduleUnit = TimeUnit.SECONDS;
    private long period = 1;
    public void setRestrictParam(long period, TimeUnit unit){
        this.period = period;
        this.scheduleUnit = unit;
    }
    
    private void start(Runnable r){
        exec.execute(r);
        exec.shutdown();
    }

    @Override
    public void run() {
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor(new ThreadFactory()
        {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "TimedSchedule");
            }
        });
        
        Future f = schedule.scheduleAtFixedRate(r, delay, period, scheduleUnit);
        try{
            f.get(timeout, unit);
        } catch(TimeoutException e1) {
        } catch (InterruptedException e2) {
        } catch (ExecutionException e3) {
        } finally {
            f.cancel(true);
            schedule.shutdown();
            
            System.out.println("Restrict Finish Run!");
        }
    }
}