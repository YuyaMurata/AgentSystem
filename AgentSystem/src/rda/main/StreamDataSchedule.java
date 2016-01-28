/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.data.SetDataType;
import rda.log.AgentSystemLogger;
import rda.queue.event.MessageQueueEvent;
import rda.queue.manager.MessageQueueManager;
import rda.queue.obj.MessageObject;
import rda.queue.reciver.ReciveMessageQueue;
import rda.window.WindowController;

/**
 *
 * @author 悠也
 */
public class StreamDataSchedule implements Runnable, SetDataType{
    private static final String name = "StreamData Scheduler";
    private static final Marker scheduleMaker = MarkerFactory.getMarker(name);
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private static Long time = -1L;
    
    private long delay, period;
    private WindowController windowControl;
    
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor(new ThreadFactory()
    {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, name);
        }
    });
    
    public StreamDataSchedule(long delay, long period, int wsize, long wait) {
        this.delay = delay;
        this.period = period;
        
        windowControl = new WindowController(wsize, wait);
    }
    
    public void start(){
        schedule.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
    }
    
    private static MessageQueueManager manager = MessageQueueManager.getInstance();
    private void send(Long t){
        MessageObject msg;
        
        while(((msg = DATA_TYPE.generate(t)) != null) && !Thread.currentThread().isInterrupted()){
            if(windowControl.pack(msg)) {
                //Get MessageQueue
                ReciveMessageQueue mq = manager.getMessageQueue(msg.id);
                
                //MessageSender
                try {
                    mq.putMessage(windowControl.get(msg.id));
                    windowControl.remove(msg.id);
                } catch (InterruptedException ex) {
                } catch (MessageQueueEvent mqev) {
                    mqev.printEvent();
                }
            }
        }
    }
    
    private void logging(){
        logger.print(scheduleMaker, 
                "Experiment Step : {} [{}ms]", new Object[]{time, 1000});
    }
    
    @Override
    public void run() {
        time++;
        logging();
        send(time);
    }
    
    public void stop(){
        //Shutdown Main
        schedule.shutdown();
        try {
            if(!schedule.awaitTermination(0, TimeUnit.SECONDS))
                schedule.shutdownNow();
        } catch (InterruptedException ex) {
            schedule.shutdownNow();
            System.out.println(name+" : Finished Scheduler !!");
        }
    }
}