/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.thread;

import java.util.ArrayList;

/**
 *
 * @author kaeru
 */
public class ThreadLoggingMain {
    public static void main(String[] args) {
        ThreadLogger mainLogger = new ThreadLogger();
        
        ArrayList<ThreadA> threadLists = new ArrayList<>();
        
        Long start, stop;
        
        
        
        for(int i=0; i < 1000; i++)
            threadLists.add(new ThreadA("n["+i+"]", mainLogger));
        
        start = System.currentTimeMillis();
        
        for(ThreadA t : threadLists) t.start();
        
        stop = System.currentTimeMillis();
        
        try {
            Thread.sleep(5000);
            mainLogger.setStop();
        } catch (InterruptedException ex) {
        } finally {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            }
            mainLogger.print();
        }
        
        
        
        System.out.println("Transaction:"+(stop-start)+"[ms]");
    }
}
