/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rda.agent.queue.MessageQueue;
import rda.manager.AgentMessageQueueManager;
import rda.manager.TestCaseManager;
import rda.queue.event.MessageQueueEvent;
import rda.queue.obj.MessageObject;
import rda.window.Window;
import rda.window.WindowController;

/**
 *
 * @author kaeru
 */
public class DataStream implements Runnable{
    private static final String name = "DataStream";
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    private static Long time, term;
    private long delay, period;
    private WindowController window;
    private Window msgPack;
    private static TestCaseManager tcmanager = TestCaseManager.getInstance();
    private Map mqMap;

    public DataStream(long delay, long period, int wsize, long wait) {
        this.delay = delay;
        this.period = period;
        
        window = new WindowController(wsize, wait);
        mqMap = AgentMessageQueueManager.getInstance().getMQMap();
    }
    
    public void start(){
        System.out.println(name + " : Start !");
        time = 0L;
        schedule.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
    }
    
    private void stream(Long t){
        MessageObject msg;
        
        while(((msg = tcmanager.datagen.generate(t)) != null) && !Thread.currentThread().isInterrupted()){
            if((msgPack = window.pack(msg)) != null) {
                //Get Destination ID
                String agID = msgPack.destID;
                
                //MessageSender
                try {
                    //Get MessageQueue
                    MessageQueue mq = (MessageQueue)mqMap.get(agID);
                    mq.put(msgPack);

                    window.remove(agID);
                } catch (MessageQueueEvent mqev) {
                    mqev.printEvent();
                }
            }
        }
    }
    
    @Override
    public void run() {
        if(term > time){
            time++;
            stream(time);
        }
    }
}
