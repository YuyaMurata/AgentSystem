/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.thread;

/**
 *
 * @author kaeru
 */
public class ThreadA extends Thread{
    private String name;
    private ThreadLogger logger;
    public ThreadA(String name, ThreadLogger logger) {
        this.name = name;
        this.logger = logger;
    }
    
    private Integer count = 0;
    private Long start, stop;
    public void run(){
        start = System.currentTimeMillis();
        
        while(logger.getRun()){
            logger.update(name, count);
            count++;
        }
        stop = System.currentTimeMillis();
        logger.stopREC(name,stop-start);
    }

}
