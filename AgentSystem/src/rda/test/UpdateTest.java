package rda.test;

import java.util.ArrayList;
import rda.agent.user.UpdateUser;
import rda.data.DataGenerator;

public class UpdateTest extends TestParameter{
	
    private static void execute(int num){
        UpdateUser user = new UpdateUser();
        
        DataGenerator gen = DataGenerator.getInstance();

        for(int i=0; i < NUMBER_OF_USER_AGENTS * num; i++){
            ArrayList<Integer> list = new ArrayList<>();
            list.add(gen.getData().data);
            user.sendUpdateMessage(gen.getData().agentKey, list);
        }
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
        
        System.out.println("N="+NUMBER_OF_USER_AGENTS+"R="+TIME_RUN+" time : "+(stop-start));
    }
}