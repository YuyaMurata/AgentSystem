/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.queue.manager.MessageQueueManager;
import rda.queue.timer.MessageQueueTimer;

/**
 *
 * @author kaeru
 */
public class FinishTask implements Runnable{
    private FutureMap fMap;
    private static final Marker finishMarker = MarkerFactory.getMarker("AgentSystem Finish Main");
    
    public FinishTask(FutureMap fMap) {
        this.fMap = fMap;
    }
    
    
    @Override
    public void run() {
        if(fMap.mainFuture.cancel(true)) System.out.println("oo MainSchedule is Cancel");
        else System.out.println("xx MainSchedule is not Cancel");
        
        if(fMap.logFuture.cancel(true))System.out.println("oo LogSchedule is Cancel");
        else System.out.println("xx LogSchedule is not Cancel");
        
        
        MessageQueueTimer.getInstance().close();
        MessageQueueManager.getInstance().stopAll();
    }
    
}
