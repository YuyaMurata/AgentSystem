/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

/**
 *
 * @author 悠也
 */
public abstract class MessageQueueProcess extends Thread{
    public void start(){
        Thread p = new Thread(this);
        p.start();
    }
    
    @Override
    public void run(){
        
        while(true){
            
        }
    }
}
