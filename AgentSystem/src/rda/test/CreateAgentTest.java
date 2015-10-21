package rda.test;

import rda.agent.CreateAgent;

public class CreateAgentTest extends TestParameter{

    private static void createUser(int numOfAgents){
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfAgents);
    }

    public static void main(String[] args) {
        Integer numOfAgents = NUMBER_OF_USER_AGENTS;
        if(args.length == 1)
            numOfAgents = Integer.valueOf(args[0]);
        //Agentの生成
        createUser(numOfAgents);
    }
}