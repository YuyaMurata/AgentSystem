/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import rda.agent.queue.MessageQueue;
import rda.agent.queue.MessageQueueEvent;
import rda.test.manager.UnitTestManager;
import rda.test.msg.MessageBox;
import rda.test.param.TestParameter;
import rda.window.Window;

/**
 *
 * @author kaeru
 */
public class MessageQueueTest extends TestParameter {
    public static void main(String[] args) {
        MessageBox.use("MessageQueue Test");
        UnitTestManager.getInstance().prepareManager();
        MessageQueue mq0 = new MessageQueue("TEST#0", QUEUE_LENGTH, 10L, 10L);
        MessageQueue mq1 = new MessageQueue("TEST#1", QUEUE_LENGTH, 10L, 10L);

        for(int i=0; i < 2*(QUEUE_LENGTH + 5); i++){
            try {
                if(i%2 == 0)
                    mq0.put(new Window("TEST",0));
                else
                    mq1.put(new Window("TEST",1));
            } catch (MessageQueueEvent mqev) {
                mqev.printEvent();
            }
        }
    }
}
