/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.run;

/**
 *
 * @author kaeru
 */
public class RunnableTimeTest implements Runnable{
    private Long time;
    public RunnableTimeTest() {
        this.time = 0L;
    }
    
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" Time = "+time);
        time++;
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
        }
    }
    
}
