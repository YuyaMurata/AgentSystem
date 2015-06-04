package rda.test;

import rda.agent.CreateAgent;
import rda.property.SetPropertry;

public class CreateTest extends SetPropertry{

	private static void create(int numOfAgents){
		CreateAgent agent = new CreateAgent();
		agent.create(numOfAgents);
	}

	public static void main(String[] args) {
		//Agentの生成
		create(NUMBER_OF_USER_AGENTS);
	}
}
