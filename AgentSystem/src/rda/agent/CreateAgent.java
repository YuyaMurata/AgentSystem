package rda.agent;

import rda.agent.user.CreateUserAgent;
import rda.queue.IDToMQN;

public class CreateAgent {
    
    public CreateAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
	
    public void create(String agentType, int numUserAgents){
        CreateUserAgent user = new CreateUserAgent();
        IDToMQN id = IDToMQN.getInstance();
        
        for(int i=0; i < numUserAgents; i++){
            String _id = agentType+i;
            id.setID(_id);
            user.create(_id);
        }	
    }	
}
