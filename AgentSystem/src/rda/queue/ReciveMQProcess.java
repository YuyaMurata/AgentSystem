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
    private static final MessageQueueTimer mqt = MessageQueueTimer.getInstance();;
    
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
        HashMap<AgentKey, ArrayList<Integer>> msgMap = new HashMap<>();
        
        MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
        
        while(mq.isRunning() || !mq.isEmpty()){
            try {
                ArrayList<MessageObject> msgList  = (ArrayList<MessageObject>) mq.getMessage();
                //System.out.println(name+"_"+messageList.size()+":稼動中!");
            
                if(msgList != null)
                    for(MessageObject msg : msgList){
                    //System.out.print("ReciveMessageQueue "+name+" execute Agent["+mes.toString()+"]");
                        if(msgMap.get(msg.agentKey) == null)
                            msgMap.put(msg.agentKey, new ArrayList<Integer>());
                        msgMap.get(msg.agentKey).add(msg.data);
                    }
            
                
                if(mq.isEmpty() || mqt.getTimer()){
                    for(AgentKey key : msgMap.keySet())
                        //user.sendUpdateMessage(key, msgMap.get(key));
                    msgMap.clear();
                    
                    mqSS.map.put(mq.name, mq.getSize());
                }
            } catch (InterruptedException e) {
            }   
        }
        
        this.finish = true;
        ag.close();
        
        logger.print(rMQMarker, 
                "********** Recive Message Queue {} Stop!! ********** ",
                new String[]{mq.name});
    }
    
    private Boolean finish = false;
    public Boolean isFinish(){
        return finish;
    }
}