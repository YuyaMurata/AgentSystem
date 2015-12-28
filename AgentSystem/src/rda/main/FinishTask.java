/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import java.util.concurrent.ScheduledExecutorService;
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
    private MainSchedule task;
    private static final Marker finishMarker = MarkerFactory.getMarker("AgentSystem Finish Main");
    
    public FinishTask(MainSchedule task, ScheduledExecutorService main, ScheduledExecutorService log) {
        this.task = task;
        this.main = main;
        this.log = log;
    }
    
    
    @Override
    public void run() {
        try{
            main.shutdownNow();
            //log.shutdownNow();
            
            System.out.println("Main Task is Cancelled !");
        }catch(Exception e){
        }finally{
            //main.shutdownNow();
            log.shutdownNow();
            
            MessageQueueTimer.getInstance().close();
            MessageQueueManager.getInstance().stopAll();
        }
    }
    
}
