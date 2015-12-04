package rda.agent.creator;

import rda.agent.user.CreateUserAgent;

public class CreateAgent {
    
    public CreateAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
	
    public void create(String agentType, int numUserAgents){
        CreateUserAgent user = new CreateUserAgent();
        
        for(int i=0; i < numUserAgents; i++){
            String id = agentType+i;
            user.create(id);
        }	
    }	
}
