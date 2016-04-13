/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.window;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import rda.manager.AgentMessageQueueManager;
import rda.queue.obj.MessageObject;

/**
 *
 * @author kaeru
 */
public class Window{
    private String originID;
    private Integer size;
    private List win = new CopyOnWriteArrayList();

    public Window(String id, Integer limit) {
        this.originID = id;
        this.size = limit;
    }
    
    public String getDestID(){
        return AgentMessageQueueManager.getInstance().getIDManager().getDestID(originID);
    }
  
    public Window pack(MessageObject msg){
        if(msg.data != -1) win.add(msg);
        
        if((win.size() >= size) || (msg.data == -1)) return this;
        
        return null;
    }
    
    public List unpack(){
        return this.win;
    }
}