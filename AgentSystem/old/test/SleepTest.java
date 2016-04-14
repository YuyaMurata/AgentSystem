/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaeru
 */
public class SleepTest {
    public static void main(String[] args) {
        String str = "Finish Sleep";
        
        long start = System.currentTimeMillis();
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
        } finally {
            long stop = System.currentTimeMillis();
            System.out.println("Finally : Time+str"+(stop-start) +str);
        }
        
        long stop = System.currentTimeMillis();
        System.out.println("Time+str"+(stop-start) +str);
    }
}
