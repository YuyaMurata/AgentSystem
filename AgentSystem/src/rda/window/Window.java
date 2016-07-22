/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.window;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import rda.data.test.DataTemplate;
import rda.manager.AgentMessageQueueManager;

/**
 *
 * @author kaeru
 */
public class Window{
    private String originID, destID;
    private Integer size;
    private WindowController manager;
    private List win = new CopyOnWriteArrayList();

    public Window(WindowController manager, String id, Integer limit) {
        this.originID = id;
        this.size = limit;
        this.manager = manager;
    }
    
    public String getOrigID(){
        return originID;
    }
    
    public String getDestID(){
        return AgentMessageQueueManager.getInstance().getIDManager().getDestID(originID);
    }
    
    public void setDestID(String id){
        destID = id;
    }
  
    public void pack(Object obj){
        DataTemplate data = (DataTemplate) obj; 
        win.add(data.getData());
        
        if(win.size() >= size) manager.addExecutable(this);
    }
    
    public List unpack(){
        return this.win;
    }
    
    public Integer getSize(){
        return this.win.size();
    }
}