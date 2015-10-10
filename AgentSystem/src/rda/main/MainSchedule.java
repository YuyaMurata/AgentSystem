/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;
import rda.queue.MessageObject;
import rda.window.WindowController;

/**
 *
 * @author kaeru
 */
public class MainSchedule implements Runnable, SetProperty{
    private Long timer;
    private final WindowController mq;
    
    private static final Marker scheduleMaker = MarkerFactory.getMarker("Main Schedule");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    Long total = 0L;
    
    public MainSchedule(WindowController win) {
        this.mq = win;
        this.timer = -1L;
    }
    
    private void sendMessage(Long t){
        MessageObject msg;
        while((msg = DATA_TYPE.getTimeToData(t)) != null){
            //mq.sendMessage(msg);
            total = total + 1;
        }
    }
    
    @Override
    public void run() {
        timer++;
        logger.print(scheduleMaker, 
                "Experiment Step : {} [{}ms]", new Object[]{timer, TIME_PERIOD});
        
        System.out.println(" Transaction of MainSchedule Total:"+total);
        
        sendMessage(timer);
    }
    
    public void isFinish(){
        mq.close();
    }
}