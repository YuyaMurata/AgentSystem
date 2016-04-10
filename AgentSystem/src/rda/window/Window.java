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
public class Window{
    private String id;
    private Integer size;
    private ArrayList win = new ArrayList();

    public Window(String id, Integer limit) {
        this.id = id;
        this.size = limit;
    }
    
    public String getDestID(){
        return id;
    }
  
    public Window pack(MessageObject msg){
        if(msg.data != -1) win.add(msg);
        
        System.out.println("WINDOW_CTL : 3 "+ id+"=" +win.size() + "/"+size + "[" +msg.toString()+"]");
        
        if((win.size() >= size) || (msg.data == -1)) return this;
        
        System.out.println("WINDOW_CTL : 4 "+ id+"=" + win.size()+"/"+size + "[" +msg.toString()+"]");
        
        return null;
    }
    
    public ArrayList unpack(){
        return this.win;
    }
}