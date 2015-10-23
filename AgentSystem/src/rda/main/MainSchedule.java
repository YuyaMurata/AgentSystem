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
import rda.queue.MessageObject;
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
        while((msg = DATA_TYPE.getTimeToData(t)) != null)
            mq.sendMessage(msg);
    }
    
    @Override
    public void run() {
        timer++;
        
        logger.print(scheduleMaker, 
                "Experiment Step : {} [{}ms]", new Object[]{timer, interval});
        
        logger.print(scheduleMaker,
                "Number Of Active:{} Idle:{}", new Object[]{
                        AgentConnection.getInstance().getActiveObject(),
                        AgentConnection.getInstance().getIdleObject()
                                });
        
        sendMessage(timer);
    }
    
    public void isFinish(){
        mq.close();
    }
}