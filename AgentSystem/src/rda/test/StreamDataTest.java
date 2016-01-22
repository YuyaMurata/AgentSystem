/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import java.util.concurrent.TimeUnit;
import rda.main.StreamDataSchedule;
import rda.timer.Restrict;

/**
 *
 * @author 悠也
 */
public class StreamDataTest {
    public static void main(String[] args) {
        System.out.println("Test Start!");
        
        Restrict rest = new Restrict();
        rest.timedRun(new StreamDataSchedule(), 0, 10, TimeUnit.SECONDS);
        
        System.out.println("Test Stop!");
    }
}
