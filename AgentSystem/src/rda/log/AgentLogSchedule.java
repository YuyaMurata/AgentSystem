/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.client.AgentConnection;
import rda.queue.log.MQSpecificStorage;

/**
 *
 * @author kaeru
 */
public class AgentLogSchedule implements Runnable{
    private static final String name = "AgentLogger Schedule";
    private static final MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private static final Marker scheduleMaker = MarkerFactory.getMarker(name);
    private static final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    
    private Long  time, delay, period, timeout;
    public AgentLogSchedule(Long delay, Long period, Long timeout) {
        this.delay = delay;
        this.period = period;
        this.timeout = timeout * (1000 / period);
        this.time = -1L;
    }
    
    private static AgentConnection conn = AgentConnection.getInstance();
    
    public void start(){
        System.out.println(name + " : Start !");
        
        schedule.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
    }
    
    public void log() throws InterruptedException {
        mqSS.mqLogging();
        
        logger.print(scheduleMaker,
            "AgentConnection Idle_{} Active_{}", 
            new Object[]{conn.getActiveObject(), conn.getIdleObject()});
    }
    
    @Override
    public void run() {
        try{
            time++;
            if(Thread.interrupted() || (time >= timeout)) throw new InterruptedException();
            
            log();
        } catch (InterruptedException e) {
            System.out.println(name + " : Finish Interrupted !");
            stop();
        }
    }
    
    public void stop(){
        //Shutdown Log
        schedule.shutdown();
        
        try {
            if(!schedule.awaitTermination(0, TimeUnit.SECONDS))
                schedule.shutdownNow();
        } catch (InterruptedException ex) {
            schedule.shutdownNow();
        }
        
        System.out.println(name + " : Stop !");
    }
}