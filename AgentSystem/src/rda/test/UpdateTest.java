package rda.test;

import rda.agent.user.UpdateUser;

public class UpdateTest extends TestParameter{
	
    private static void execute(int num){
        UpdateUser user = new UpdateUser();
        
    }

    public static void main(String[] args) {
        //*** After CreateAgentTest ***//
        
        //Update Agent
        long start = System.currentTimeMillis();
	System.out.println("Start Agent System : "+start);
        
        //Executable Test
        execute(TIME_RUN);
        
        long stop = System.currentTimeMillis();
        System.out.println("Stop Agent System : "+stop);
        
        System.out.println("N="+NUMBER_OF_USER+"R="+TIME_RUN+" time : "+(stop-start));
    }
}