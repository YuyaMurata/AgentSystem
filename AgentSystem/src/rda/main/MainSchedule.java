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
    private static int timer = -1;
    private final WindowController mq;
    
    private static final Marker scheduleMaker = MarkerFactory.getMarker("Main Schedule");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    long total = 0;
    
    public MainSchedule(WindowController win) {
        this.mq = win;
    }
    
    private void sendMessage(int t){
        MessageObject msg;
        while((msg = DATA_TYPE.getTimeToData(t)) != null){
            mq.sendMessage(msg);
            total = total + msg.data;
        }
    }
    
    @Override
    public void run() {
        timer++;
        logger.print(scheduleMaker, 
                "Experiment Step : {} [{}ms]", new Object[]{timer, TIME_PERIOD});
        
        sendMessage(timer);
    }
    
    public void isFinish(){
        mq.close();
        
        System.out.println(" Transaction of MainSchedule Total:"+total);
    }
}