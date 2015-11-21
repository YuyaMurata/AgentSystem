/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.log;

import rda.queue.log.MQSpecificStorage;

/**
 *
 * @author kaeru
 */
public class AgentLogSchedule implements Runnable{
    private static final MQSpecificStorage mqSS = MQSpecificStorage.getInstance();

    public AgentLogSchedule() {
    }
    
    @Override
    public void run() {
        mqSS.mqLogging();
    }
    
}
