package rda.queue;

import com.ibm.agent.exa.AgentKey;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import rda.agent.CreateAgentClient;
import rda.agent.user.UpdateUser;
import rda.property.SetProperty;

public class ReciveMQProcess extends Thread {	
    private final String name;
    private final ReciveMessageQueue mq;
    private final MessageQueueTimer mqt;
    private final CreateAgentClient ag = new CreateAgentClient();
    private static final Marker rMQMarker = MarkerFactory.getMarker("ReciveMessageQueue");
    
    public ReciveMQProcess(String name, ReciveMessageQueue queue) {
        SetProperty.logger.info(rMQMarker, "********** Recive Message Queue {} Start!! ********** ", name);
        
        this.name = name;
        this.mq = queue;
        
        this.mqt = MessageQueueTimer.getInstance();
    }

    public void run() {
        UpdateUser user = new UpdateUser();
        user.setParam(ag.getClient());
        HashMap<AgentKey, ArrayList<Integer>> msgMap = new HashMap<>();
		
        while(running){
            ArrayList<MessageObject> msgList = (ArrayList<MessageObject>) mq.getMessage();
            //System.out.println(name+"_"+messageList.size()+":稼動中!");
			
            for(MessageObject msg : msgList){
                //System.out.print("ReciveMessageQueue "+name+" execute Agent["+mes.toString()+"]");
                if(msgMap.get(msg.agentKey) == null)
                    msgMap.put(msg.agentKey, new ArrayList<Integer>());
                msgMap.get(msg.agentKey).add(msg.data);
            }
                
            if(mq.isEmpty() || mqt.getTimer()){
                for(AgentKey key : msgMap.keySet())
                    user.sendUpdateMessage(key, msgMap.get(key));
                msgMap.clear();
                
                //MQSpecificStorage.setMQSSMap(name, mq.getSize());
            }
        }
        
        SetProperty.logger.info(rMQMarker, "********** Recive Message Queue {} Stop!! ********** ", name);
    }

    private boolean running = true;
    public void stopRunning(){
        running = false;
        ag.close();
    }
}
