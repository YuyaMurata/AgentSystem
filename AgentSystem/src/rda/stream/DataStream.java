package rda.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rda.agent.queue.MessageObject;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.MessageQueueEvent;
import rda.data.test.TestData;
import rda.data.type.RouletteData;
import rda.manager.AgentManager;
import rda.manager.TestCaseManager;
import rda.window.Window;

public class DataStream implements Runnable{
    private static final String name = "DataStream";
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    private Long time, term, total=0L;
    private long delay, period;
    private Boolean runnable;
    
    private static TestCaseManager tcmanager = TestCaseManager.getInstance();
    private AgentManager manager;
    
    public DataStream(AgentManager manager, Map streamMap) {
        this.term = (Long)streamMap.get("TIME_RUN");
        this.period = (Long)streamMap.get("TIME_PERIOD");
        this.manager = manager;
    }
    
    public void start(){
        System.out.println("> "+name + " : Start !");
        runnable = true;
        time = 0L;
        schedule.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
    }
    
    private Random rand = new Random();
    private Map idcheckLog = new HashMap();
    private void stream(Long t){
        Map mqMap = manager.getMQMap();
        TestData data;
        Window window;
        
        //Roulette
        ((RouletteData)tcmanager.datagen.getType()).roulette(rand.nextInt(100));
        idcheckLog = new TreeMap();
        
        while(((data = tcmanager.datagen.generate(t)) != null) && runnable){
            try {
                manager.getWindowController().pack(data);
                
                if((window = manager.getWindowController().get()) == null) continue;
                
                //Get Destination ID
                String agID = window.getDestID();
                
                if(idcheckLog.get(agID) == null) idcheckLog.put(agID, 0);
                idcheckLog.put(agID, (int)idcheckLog.get(agID)+1);
            
                //Translation Window To Message
                MessageObject msg = new MessageObject(agID, window.unpack());
                
                //Get MessageQueue
                MessageQueue mq = (MessageQueue)mqMap.get(agID);
                
                //Message Sender
                mq.put(msg);
                
                //Agent Put Handler
                //new SendAgentMessage().sendMessage(msgPack);
                
                total = total+window.unpack().size();
                
                manager.getWindowController().remove();
            } catch (MessageQueueEvent mqev) {
                    mqev.printEvent();
            } catch (Exception e){
                    e.printStackTrace();
            }
        }
        
        System.out.println(idcheckLog);
    }
    
    @Override
    public void run() {
        if(term+1 > time){
            System.out.println("-Time Period = "+time);
            stream(time);
            tcmanager.debugTestGenerateCounts(total);
            time++;
        }
    }
    
    public void stop(){
        try {
            Thread.sleep(term*1000);
        } catch (InterruptedException ex) {
        }
        
        runnable = false;
        
        System.out.println("> " + name +" : Stop !");
        
        try {
            schedule.shutdown();
            if(!schedule.awaitTermination(0, TimeUnit.SECONDS))
                schedule.shutdownNow();
        } catch (InterruptedException ex) {
            schedule.shutdownNow();
        }
        
        tcmanager.debugTestGenerateCounts(total);
    }
}