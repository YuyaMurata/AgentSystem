package rda.queue.reciver;

import java.util.ArrayList;
import rda.agent.user.creator.CreateUserAgent;

import rda.agent.user.updater.UpdateUser;
import rda.queue.obj.MessageObject;
import rda.queue.timer.MessageQueueTimer;

public class ReciveMQProcess extends Thread{
    private final ReciveMessageQueue mq;
    
    public ReciveMQProcess(ReciveMessageQueue queue) {
        this.mq = queue;
    }

    @Override
    public void run() {
        MessageQueueTimer mqt = MessageQueueTimer.getInstance();
        String agID = mq.name;
        
        //Create Agent
        CreateUserAgent agent = new CreateUserAgent();
        agent.create(agID);
        
        //Setting Update
        UpdateUser user = new UpdateUser();
        
        ArrayList<Integer> dataList = new ArrayList<>();
        
        while(true){
            try{
                synchronized(this){
                    if(!mq.isRunning()) break;
                }
                
                for(MessageObject msg : (ArrayList<MessageObject>)mq.getMessage()){
                    dataList.add(msg.data);
                }
            
                if(mq.isEmpty() || mqt.getTimer()){
                    user.sendUpdateMessage(agID, dataList);
                    dataList.clear();
                }
                
            } catch (InterruptedException e) {}
        }
    } 
}