package rda.queue.reciver;

import com.ibm.agent.exa.AgentKey;
import java.util.ArrayList;

import rda.agent.user.UpdateUser;
import rda.queue.id.IDToMQN;
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
        IDToMQN id = IDToMQN.getInstance();
        
        UpdateUser user = new UpdateUser();
        
        ArrayList<Integer> dataList = new ArrayList<>();
        AgentKey key = id.agIDToKey(mq.name);
        
        while(true){
            try{
                synchronized(this){
                    if(!mq.isRunning()) break;
                }
                    
                for(MessageObject msg : (ArrayList<MessageObject>)mq.getMessage()){
                    dataList.add(msg.data);
                }
            
                if(mq.isEmpty() || mqt.getTimer()){
                    user.sendUpdateMessage(key, dataList);
                    dataList.clear();
                }
            } catch (InterruptedException e) {}
        }
    } 
}