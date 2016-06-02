/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import rda.agent.template.AgentType;
import rda.clone.AgentCloning;
import rda.manager.AgentMessageQueueManager;
import rda.manager.IDManager;
import rda.window.Window;

/**
 *
 * @author 悠也
 */
public class MessageQueue extends MessageQueueProcess{
    private BlockingQueue<Object> queue;
    public String name;
    private AgentType agent;
    private Integer size;
    private long getwait, putwait;
    
    public MessageQueue(String name, Integer size, Long queuewait, Long agentwait){
        this.name = name;
        this.size = size;
        this.getwait = agentwait;
        this.putwait = queuewait;
        this.queue = new ArrayBlockingQueue<>(size+1);
        
        //Message Queue Length @RECORDS
        QueueObserver observe = new QueueObserver(name, queue);
        register(observe);
    }
    
    private void register(QueueObserver observe){
        AgentMessageQueueManager.getInstance().add(observe);
    }
    
    @Override
    public Object get(){
        try {
            return queue.poll(getwait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
        }
        
        return null;
    }
    
    @Override
    public void put(Object msgpack) throws MessageQueueEvent{
        boolean success = false;
        try {
            success = queue.offer(msgpack, putwait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
        }
        
        if(!success) event(msgpack);
    }
    
    public void event(Object msgpack) throws MessageQueueEvent{
        AgentMessageQueueManager agent = AgentMessageQueueManager.getInstance();
        String agID = "";
        if(agent.getAutoMode() == 1){
            //if(clone != null)   
            //    System.out.println(">> Current Agent ["+this.name+"] Before Clone--Original ID = "+clone.originalID + ", Clone ID = "+clone.cloneID);
            IDManager id = agent.getIDManager();
            agID = id.genID();
            agent.createAgent(agID);
            id.regID(((Window)msgpack).getOrigID(), agID);
            //((Window)msgpack).setDestID(agID);
        }
        //AgentCloning agentCloning = new AgentCloning(this.queue);
        //String agID = agentCloning.cloning(((Window)msgpack).getOrigID());
        
        throw new MessageQueueEvent(name, agID, msgpack);
    }
    
    //Only AgnetClone
    private AgentCloning clone;
    public void setOriginalQueue(AgentCloning clone){
        this.clone = clone;
    }
    
    //MessageQueue Process Overrides
    @Override
    public Boolean getRunnable() {
        return AgentMessageQueueManager.getInstance().getState();
    }

    @Override
    public void setAgentType(AgentType type) {
        this.agent = type;
    }

    @Override
    public AgentType getAgentType() {
        return this.agent;
    }
}