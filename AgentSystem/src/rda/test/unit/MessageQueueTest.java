/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import rda.agent.queue.MessageObject;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.MessageQueueEvent;
import rda.log.AgentSystemLogger;
import rda.test.msg.MessageBox;
import rda.test.param.TestParameter;

/**
 *
 * @author kaeru
 */
public class MessageQueueTest extends TestParameter {
    private static AgentSystemLogger logger = AgentSystemLogger.getInstance();
    public static void main(String[] args) {
        MessageBox.use("MessageQueue Test");
        MessageQueue mq0 = new MessageQueue("TEST#0", QUEUE_LENGTH, 10L, 10L);
        MessageQueue mq1 = new MessageQueue("TEST#1", QUEUE_LENGTH, 10L, 10L);

        for(int i=0; i < 2*(QUEUE_LENGTH + 5); i++){
            try {
                if(i%2 == 0)
                    mq0.put(new MessageObject("TEST#0", i, "TEST#0"));
                else
                    mq1.put(new MessageObject("TEST#1", i, "TEST#1"));
            } catch (MessageQueueEvent mqev) {
                System.out.println("MQEvents!");
                //mqev.printEvent();
            }
        }
    }
}
