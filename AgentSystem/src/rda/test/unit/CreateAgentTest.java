package rda.test.unit;

import rda.agent.creator.CreateAgent;
import rda.agent.user.creator.CreateRankAgent;
import rda.test.setter.TestParameter;

public class CreateAgentTest extends TestParameter{

    public void createAgent(int numOfAgents){
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfAgents);
    }
    
    public void createAgent(String agID){
        CreateRankAgent user = new CreateRankAgent();
        user.create(agID);
    }
}