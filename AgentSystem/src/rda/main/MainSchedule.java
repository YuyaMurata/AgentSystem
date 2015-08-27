/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import rda.data.MountData;
import rda.property.SetPropertry;
import rda.queue.MessageObject;
import rda.queue.WindowController;

/**
 *
 * @author kaeru
 */
public class MainSchedule extends SetPropertry implements Runnable{
    private static int timer = -1;
    private final WindowController mq;

    public MainSchedule(WindowController win) {
        this.mq = win;
    }
    
    private static final MountData mt = new MountData();
    private void sendMessage(int t){
        MessageObject msg;
        while((msg = mt.getTimeToData(t)) != null){
            mq.sendMessage(msg);
            //System.out.println("Message:"+msg.agentKey+"."+msg.data);
        }
    }
    
    @Override
    public void run() {
        timer++;
        System.out.println("Experiment Step :" + timer +" sec");

        //Output Queue Length
        //mq.outputMQLog(timer);
        sendMessage(timer);
    }
    
    public void close(){
        mq.close();
    }
}
