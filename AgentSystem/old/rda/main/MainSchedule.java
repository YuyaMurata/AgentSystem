/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.data.SetDataType;
import rda.log.AgentSystemLogger;
import rda.queue.obj.MessageObject;
import rda.window.WindowController;

/**
 *
 * @author kaeru
 */
public class MainSchedule implements Runnable, SetDataType{
    private Long timer;
    //private WindowController mq;
    
    private static final Marker scheduleMaker = MarkerFactory.getMarker("Main Schedule");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    //private static final ScheduledExecutorService mainTask = Executors.newSingleThreadScheduledExecutor();
    
    private Long period, delay;
    public MainSchedule(Long delay, Long period) {
        //this.mq = win;
        this.period = period;
        this.delay = delay;
        
        //Initialise Time Step
        this.timer = -1L;
    }
    
    public void start(){
        //mainTask.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
    }
    
    private void sendMessage(Long t){
        //try {
        MessageObject msg;
        while(((msg = DATA_TYPE.generate(t)) != null) && !Thread.currentThread().isInterrupted()){
            //if(mq.pack(msg)) mq.send(msg.id);
            //mq.sendMessage(msg);
        }
        //}catch(InterruptedException e){}
    }
    
    private void logging(){
        logger.print(scheduleMaker, 
                "Experiment Step : {} [{}ms]", new Object[]{timer, period});
    }
    
    @Override
    public void run() {
        timer++;
            
        logging();
            
        //sendMessage(timer); 
    }
    
    public void stop(){
        //Shutdown Main
        /*mainTask.shutdown();
        try {
            if(!mainTask.awaitTermination(0, TimeUnit.SECONDS))
                mainTask.shutdownNow();
        } catch (InterruptedException ex) {
            mainTask.shutdownNow();
        }*/
    }
}