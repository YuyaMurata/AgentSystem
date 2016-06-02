/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author 悠也
 */
public class QueueDequeueTest {
    public static void main(String[] args) {
        BlockingQueue q1 = new LinkedBlockingQueue();
        q1.add("TEST1");
        q1.add("TEST2");
        q1.add("TEST3");
        LinkedBlockingDeque q2 = new LinkedBlockingDeque(q1);
        
        System.out.println(q1.poll());
        System.out.println(q2.poll());
        System.out.println(q1.poll());
        System.out.println(q2.poll());
    }
}
