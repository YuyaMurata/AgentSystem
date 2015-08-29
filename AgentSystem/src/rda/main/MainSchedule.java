/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.property.SetProperty;
import rda.queue.MessageObject;
import rda.queue.WindowController;

/**
 *
 * @author kaeru
 */
public class MainSchedule implements Runnable, SetProperty{
    private static int timer = -1;
    private final WindowController mq;
    private static final Marker scheduleMaker = MarkerFactory.getMarker("Main Schedule");

    public MainSchedule(WindowController win) {
        this.mq = win;
    }
    
    private void sendMessage(int t){
        MessageObject msg;
        while((msg = DATA_TYPE.getTimeToData(t)) != null){
            mq.sendMessage(msg);
        }
    }
    
    @Override
    public void run() {
        timer++;
        logger.info(scheduleMaker,"Experiment Step : {} [{}ms]", timer, TIME_PERIOD);

        //Output Queue Length
        //mq.outputMQLog(timer);
        sendMessage(timer);
    }
    
    public void close(){
        mq.close();
    }
}
