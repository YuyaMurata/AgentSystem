/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import rda.agent.client.AgentConnection;
import rda.property.SetProperty;
import rda.queue.timer.MessageQueueTimer;
import rda.queue.id.IDToMQN;

/**
 *
 * @author 悠也
 */
public class SystemManager implements SetProperty{
    private static SystemManager manager = new SystemManager();
    private SystemManager(){}
    
    public static SystemManager getInstance(){
        return manager;
    }
    
    public void launchSystem(){
        AgentConnection.getInstance().setPoolSize(POOLSIZE);
        
        IDToMQN.getInstance().setNumQueue(NUMBER_OF_QUEUE);
        
        MessageQueueTimer.getInstance().setTImerPriod(QUEUE_WAIT);
    }
}
