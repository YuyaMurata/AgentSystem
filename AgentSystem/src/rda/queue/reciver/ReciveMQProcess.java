package rda.queue.reciver;

import java.util.ArrayList;
import java.util.HashMap;
import rda.agent.user.creator.CreateUserAgent;

import rda.agent.user.updater.UpdateUser;
import rda.queue.id.IDToMQN;
import rda.queue.obj.MessageObject;
import rda.queue.timer.MessageQueueTimer;
import rda.window.Window;

public class ReciveMQProcess extends Thread{
    private final ReciveMessageQueue mq;
    private Boolean running = false;
    
    public ReciveMQProcess(ReciveMessageQueue queue) {
        this.mq = queue;
        super.setName(queue.name);
    }

    @Override
    public void run() {
        this.running = true;
        
        MessageQueueTimer mqt = MessageQueueTimer.getInstance();
        String agID = mq.name;
        
        //ID
        IDToMQN id = IDToMQN.getInstance();
        
        //Create Agent
        //CreateUserAgent agent = new CreateUserAgent();
        //agent.create(agID);
        
        //Setting Update
        UpdateUser user = new UpdateUser();
        
        HashMap<String, ArrayList> dataMap = new HashMap();
        
        while(true){
            try{
                synchronized(this){
                    if(!mq.isRunning()) break;
                }
                
                Window window = (Window) mq.getMessage();
                for(MessageObject msg : (ArrayList<MessageObject>) window.get()){
                    if(dataMap.get(msg.id) == null) dataMap.put(msg.id, new ArrayList());
                    dataMap.get(msg.id).add(msg.data);
                }
            
                if(mqt.getTimer()){
                    for(String uid : dataMap.keySet()){
                        user.sendUpdateMessage(agID, dataMap.get(uid));
                        //user.sendUpdateMessage(id.ageToAGID(uid), dataMap.get(uid));
                        dataMap.get(uid).clear();
                    }
                }
                
            } catch (InterruptedException e) {}
        }
    }
}
