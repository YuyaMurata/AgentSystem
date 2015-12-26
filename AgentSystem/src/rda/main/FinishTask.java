/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;
import rda.queue.manager.MessageQueueManager;
import rda.queue.timer.MessageQueueTimer;

/**
 *
 * @author kaeru
 */
public class FinishTask implements Runnable{
    private ScheduledExecutorService main, log;
    private ScheduledFuture future;
    private static final Marker finishMarker = MarkerFactory.getMarker("AgentSystem Finish Main");
    
    public FinishTask(ScheduledFuture future, ScheduledExecutorService main, ScheduledExecutorService log) {
        this.future = future;
        this.main = main;
        this.log = log;
    }
    
    
    @Override
    public void run() {
        //future.cancel(true);
        
        AgentSystemLogger logger = AgentSystemLogger.getInstance();
        logger.print(finishMarker, "Main Task is Cancelled !", null);
        
        try{
            main.shutdownNow();
            //main.shutdown();
            log.shutdown();
            
            MessageQueueTimer.getInstance().close();
            MessageQueueManager.getInstance().stopAll();
        
        }catch(Exception e){
        }finally{
            main.shutdownNow();
            log.shutdownNow();
        }
    }
    
}
