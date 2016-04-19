/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rda.agent.queue.QueueObserver;
import rda.manager.LoggerManager;

/**
 *
 * @author kaeru
 */
public class LogSchedule implements Runnable{
    private static final String name = "LogSchedule";
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    private Long time, term, start, stop;
    private long delay, period;
    private Boolean runnable;
    
    private List<QueueObserver> observes;
    
    public LogSchedule(Map loggerMap) {
        this.term = (Long)loggerMap.get("TIME_RUN");
        this.period = (Long)loggerMap.get("TIME_PERIOD");
    }
    
    public void start(){
        System.out.println("> "+name + " : Start !");
        runnable = true;
        time = 0L;
        
        schedule.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
        start = System.currentTimeMillis();
        loggerTime("StratTime", String.valueOf(start));
    }
    
    private void logging(Long time){
        try{
        LoggerManager.getInstance().printTestcaseData(time);
        LoggerManager.getInstance().printQueueObserever();
        LoggerManager.getInstance().printAgentDBTranData();
        LoggerManager.getInstance().printMessageLatency();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        if(term > time){
            time++;
            logging(time);
        }
    }
    
    public void stop(){       
        runnable = false;
        
        System.out.println("> " + name +" : Stop !");
        
        try {
            schedule.shutdown();
            if(!schedule.awaitTermination(0, TimeUnit.SECONDS))
                schedule.shutdownNow();
        } catch (InterruptedException ex) {
            schedule.shutdownNow();
        }
        
        stop = System.currentTimeMillis();
        loggerTime("StopTime", String.valueOf(stop));
        LoggerManager.getInstance().printAgentDBLifeData(start);
        
        loggerTime("TransactionTime", String.valueOf(stop-start));
        
        logging(-1L);        
    }
    
    private void loggerTime(String key, String value){
        Map map = new HashMap();
        map.put(key, value);
        AgentLogPrint.printResults("", map);
    }
}
