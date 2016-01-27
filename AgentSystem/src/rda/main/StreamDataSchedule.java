/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;

/**
 *
 * @author 悠也
 */
public class StreamDataSchedule implements Runnable{
    private static final Marker scheduleMaker = MarkerFactory.getMarker("Main Schedule");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private Long time = -1L;
    
    private void logging(){
        logger.print(scheduleMaker, 
                "Experiment Step : {} [{}ms]", new Object[]{time, 1000});
    }
    
    @Override
    public void run() {
        time++;
        logging();
    }
}
