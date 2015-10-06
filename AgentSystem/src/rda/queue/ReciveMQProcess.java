package rda.queue;

import com.ibm.agent.exa.AgentKey;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import rda.agent.CreateAgentClient;
import rda.agent.user.UpdateUser;
import rda.log.AgentSystemLogger;

public class ReciveMQProcess extends Thread{
    private final ReciveMessageQueue mq;
    private static final MessageQueueTimer mqt = MessageQueueTimer.getInstance();
    
    private static final Marker rMQMarker = MarkerFactory.getMarker("ReciveMessageQueue");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    public ReciveMQProcess(ReciveMessageQueue queue) {
        this.mq = queue;
        
        logger.print(rMQMarker, 
                "********** Recive Message Queue {} Start!! ********** ", 
                new String[]{mq.name});
    }

    @Override
    public void run() {
        CreateAgentClient ag = new CreateAgentClient();
                
        UpdateUser user = new UpdateUser();
        user.setParam(ag.getClient());
        
        ArrayList<Integer> dataList = new ArrayList<>();
        AgentKey key = null;
        
        MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
        
        try{
            while(true){
                try{
                    synchronized(this){
                        if(!mq.isRunning()) break;
                    }
                    //ArrayList<MessageObject> msgList  = (ArrayList<MessageObject>) mq.getMessage();
            
                    //if(msgList != null)
                        for(MessageObject msg : (ArrayList<MessageObject>)mq.getMessage()){
                            //System.out.print("ReciveMessageQueue "+name+" execute Agent["+mes.toString()+"]");
                            dataList.add(msg.data);
                            key = msg.agentKey;
                        }
            
                    if(mq.isEmpty() || mqt.getTimer()){
                        user.sendUpdateMessage(key, dataList);
                        dataList.clear();
                
                        mqSS.map.put(mq.name, mq.getSize());
                    }
                } catch (InterruptedException e) {}
            }
        } finally {
        
            ag.close();
        
            logger.print(rMQMarker, 
                "********** Recive Message Queue {} Stop!! ********** ",
                new String[]{mq.name});
        }
    } 
}