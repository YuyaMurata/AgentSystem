/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import rda.agent.queue.MessageQueue;
import rda.queue.event.MessageQueueEvent;
import rda.test.setter.TestParameter;

/**
 *
 * @author kaeru
 */
public class MessageQueueTest extends TestParameter {
    public static void main(String[] args) {
        MessageQueue mq = new MessageQueue("TEST#0", QUEUE_LENGTH);
        
        for(int i=0; i < QUEUE_LENGTH+1; i++)
            try {
                mq.put("Test");
            } catch (MessageQueueEvent ev) {
                ev.printEvent();
            }
    }
}
