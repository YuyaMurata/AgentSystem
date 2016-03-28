package rda.test.unit;

import rda.agent.rank.creator.CreateRankAgent;
import rda.agent.user.creator.CreateUserAgent;
import rda.test.setter.TestParameter;

public class CreateAgentTest extends TestParameter{
    public static void main(String[] args) {
        //User Agents
        //createUserAgents(NUMBER_OF_AGENTS);
        
        //Rank Agents
        createRankAgents(NUMBER_OF_AGENTS);
    }
    
    public static void createUserAgents(Integer numberOfUserAgents){
        CreateUserAgent userAgent = new CreateUserAgent();
        
        for(int i=0; i < numberOfUserAgents; i++)
            userAgent.create("U#00"+i);
    }
    
    public static void createRankAgents(Integer numberOfRankAgents){
        CreateRankAgent rankAgent = new CreateRankAgent();
        
        for(int i=0; i < numberOfRankAgents; i++)
            rankAgent.create("R#00"+i, QUEUE_LENGTH);
    }
}