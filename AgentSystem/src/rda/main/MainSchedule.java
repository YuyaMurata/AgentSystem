/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final WindowController mq;
    private final Long interval;
    
    private static final Marker scheduleMaker = MarkerFactory.getMarker("Main Schedule");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    public MainSchedule(WindowController win, Long interval) {
        this.mq = win;
        this.interval = interval;
        
        //Initialise Time Step
        this.timer = -1L;
    }
    
    private void sendMessage(Long t) throws InterruptedException{
        MessageObject msg;
        while((msg = DATA_TYPE.generate(t)) != null)
            mq.sendMessage(msg);
    }
    
    private void logging(){
        logger.print(scheduleMaker, 
                "QS:{} Experiment Step : {} [{}ms]", new Object[]{mq.queue.size(), timer, interval});
    }
    
    @Override
    public void run() {
        try {
            timer++;
            
            logging();
            
            sendMessage(timer);
        } catch (InterruptedException ex) {
            System.out.println("Main Schedule Finish Interrupted!");
        }
    }
    
    public void finish(){
        mq.close();
    }
}