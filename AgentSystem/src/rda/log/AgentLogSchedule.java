/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.log;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.client.AgentConnection;
import rda.queue.log.MQSpecificStorage;

/**
 *
 * @author kaeru
 */
public class AgentLogSchedule implements Runnable{
    private static final MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private static final Marker scheduleMaker = MarkerFactory.getMarker("Logger Schedule");

    public AgentLogSchedule() {
    }
    
    private AgentConnection conn = AgentConnection.getInstance();
    
    public void logging(){
        logger.print(scheduleMaker,
            "AgentConnection Idle_{} Active_{}", 
            new Object[]{conn.getActiveObject(), conn.getIdleObject()});
    }
    
    @Override
    public void run() {
        try{
            mqSS.mqLogging();
            
            logging();
        } catch (InterruptedException e) {
            System.out.println("Logging Schedule Finish Interrupted!");
        }
    }
    
}
