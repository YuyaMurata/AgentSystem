package rda.agent;

import rda.agent.user.CreateUserAgent;

public class CreateAgent {
	private final CreateAgentClient ag;
	
	public CreateAgent() {
		// TODO 自動生成されたコンストラクター・スタブ
		this.ag = new CreateAgentClient();
	}
	
	public void create(String agentType, int numUserAgents){
		CreateUserAgent user = new CreateUserAgent(ag.getClient());
		
		for(int i=0; i < numUserAgents; i++){
			user.create(agentType+i);
		}
		
		ag.close();
	}
	
}
