/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

import java.util.Queue;

/**
 *
 * @author 悠也
 */
public class QueueObserver {
    private String name;
    private final Integer queue;

    public QueueObserver(String name, Integer queueSize) {
        this.name = name;
        this.queue = queueSize;
    }
    
    public String getName(){
        return name;
    }
    
    public Integer notifyState(){
        return queue;
    }
}