/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.thread;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author kaeru
 */
public class ThreadLogger {
    private static ConcurrentHashMap map = new ConcurrentHashMap();
    public void update(String name, Integer value){
        map.put(name, value);
    }
    
    public void print(){
        for(String name: (Set<String>)map.keySet()){
            //System.out.println(name+":"+map.get(name));
            System.out.println(name+","+(Long)stopMap.get(name)+"[ms]");
        }
    }
    
    private volatile Boolean runnable = true;
    public Boolean getRun(){
        return runnable;
    }
    public void setStop(){
        runnable = false;
    }
    
    private static ConcurrentHashMap stopMap = new ConcurrentHashMap();
    public void stopREC(String name, Long time){
        stopMap.put(name, time);
    }
}