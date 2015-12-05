package rda.test.unit;

import rda.agent.creator.CreateAgent;
import rda.agent.user.CreateUserAgent;
import rda.test.TestParameter;

public class CreateAgentTest extends TestParameter{

    public void createAgent(int numOfAgents){
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfAgents);
    }
    
    public void createAgent(String agID){
        CreateUserAgent user = new CreateUserAgent();
        user.create(agID);
    }
}