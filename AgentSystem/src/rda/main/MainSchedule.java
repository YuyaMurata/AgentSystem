/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.client.AgentConnection;
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
    
    private void sendMessage(Long t){
        MessageObject msg;
        while((msg = DATA_TYPE.generate(t)) != null)
            mq.sendMessage(msg);
    }
    
    private AgentConnection conn = AgentConnection.getInstance();
    private void logging(){
        logger.print(scheduleMaker, 
                "QS:{} Experiment Step : {} [{}ms]", new Object[]{mq.queue.size(), timer, interval});
        
        logger.print(scheduleMaker,
                "AgentConnection Idle_{} Active_{}", new Object[]{conn.getActiveObject(), conn.getIdleObject()});
    }
    
    @Override
    public void run() {
        timer++;
        
        logging();
        
        sendMessage(timer);
    }
    
    public void isFinish(){
        mq.close();
    }
}