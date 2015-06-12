package rda.test;

import rda.agent.CreateAgent;

public class CreateAgentTest{

    private static void createUser(int numOfAgents){
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfAgents);
    }

    public static void main(String[] args) {
        int number = 100;
        if(!args.equals(""))
            number = Integer.parseInt(args[0]);
        //Agentの生成
        createUser(number);
    }
}