package rda.agent.creator;

import rda.agent.user.creator.CreateRankAgent;

public class CreateAgent {
    
    public CreateAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
	
    public void create(String agentType, int numUserAgents){
        CreateRankAgent user = new CreateRankAgent();
        
        for(int i=0; i < numUserAgents; i++){
            String id = agentType+i;
            user.create(id);
        }	
    }	
}