/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.stream;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.QueueObserver;
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
    private Long time, term;
    private long delay, period, wait;
    private WindowController window;
    private Window msgPack;
    private static TestCaseManager tcmanager = TestCaseManager.getInstance();
    private Map mqMap;

    public DataStream(Map streamMap) {
        this.term = (Long)streamMap.get("TIME_RUN");
        this.period = (Long)streamMap.get("TIME_PERIOD");
        
        window = new WindowController((Integer)streamMap.get("WINDOW_SIZE"), (Long)streamMap.get("TIME_WAIT"));
        mqMap = AgentMessageQueueManager.getInstance().getMQMap();
    }
    
    public void start(){
        System.out.println("> "+name + " : Start !");
        time = 0L;
        schedule.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
    }
    
    private void logging(){
        List<QueueObserver> observes = AgentMessageQueueManager.getInstance().getObserver();
        for(QueueObserver observe : observes)
            System.out.println(">>OBSERVE : "+observe.getName() + "-" + observe.notifyState());
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
                    //mqev.printEvent();
                }
                
                System.out.println("    >check MSG_OBJ::"+msg.toString());
            }
        }
    }
    
    @Override
    public void run() {
        if(term > time){
            System.out.println("-Time Period = "+time++);
            logging();
            stream(time);
        }
    }
    
    public void stop(){
        try {
            Thread.sleep(term*1000);
        } catch (InterruptedException ex) {
        }
        
        System.out.println("> " + name +" : Stop !");
        
        try {
            schedule.shutdown();
            if(!schedule.awaitTermination(1, TimeUnit.SECONDS))
                schedule.shutdownNow();
        } catch (InterruptedException ex) {
            schedule.shutdownNow();
        }
        
        logging();
            
    }
}