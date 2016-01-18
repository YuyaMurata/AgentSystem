/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

/**
 *
 * @author kaeru
 */
public class DataStreamScheduler implements Runnable{
    private Long timer;
    
    public DataStreamScheduler() {
        this.timer = 0L;
    }
    
    
    @Override
    public void run() {
        System.out.print("Experiments time : ");
        System.out.println(timer);
        
        timer++;
    }
    
}
