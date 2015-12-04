/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.window;

import java.util.ArrayList;
import rda.queue.obj.MessageObject;

/**
 *
 * @author kaeru
 */
public class Window implements Cloneable{
    public String id;
    public Integer limit;
    private ArrayList win = new ArrayList();

    public Window(String id, Integer limit) {
        this.id = id;
        this.limit = limit;
    }
  
    public Boolean add(MessageObject msg){
        if(msg.data != -1) win.add(msg);
        
        if(win.size() >= limit || msg.data != -1) return true;
        
        return false;
    }
    
    public ArrayList get(){
        return this.win;
    }
    
    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString());
        }
    }
}
