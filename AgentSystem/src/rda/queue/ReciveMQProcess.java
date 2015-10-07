package rda.queue;

import com.ibm.agent.exa.AgentKey;
import java.util.ArrayList;

import rda.agent.CreateAgentClient;
import rda.agent.user.UpdateUser;

public class ReciveMQProcess extends Thread{
    private final ReciveMessageQueue mq;
    private static final MessageQueueTimer mqt = MessageQueueTimer.getInstance();
    
    public ReciveMQProcess(ReciveMessageQueue queue) {
        this.mq = queue;
    }

    @Override
    public void run() {
        CreateAgentClient ag = new CreateAgentClient();
        UpdateUser user = new UpdateUser(ag.getClient());
        
        ArrayList<Integer> dataList = new ArrayList<>();
        AgentKey key = null;
        
        try{
            while(true){
                try{
                    synchronized(this){
                        if(!mq.isRunning()) break;
                    }
                    
                    for(MessageObject msg : (ArrayList<MessageObject>)mq.getMessage()){
                        dataList.add(msg.data);
                        key = msg.agentKey;
                    }
            
                    if(mq.isEmpty() || mqt.getTimer()){
                        user.sendUpdateMessage(key, dataList);
                        dataList.clear();
                        mq.log();
                    }
                } catch (InterruptedException e) {}
            }
        } finally {
            ag.close();
        }
    } 
}