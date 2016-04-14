package rda.test;

import rda.test.param.TestParameter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.agent.creator.CreateAgent;

import rda.data.fileout.OutputData;
import rda.queue.obj.MessageObject;
import rda.window.WindowController;

public class MessageQueueTest extends TestParameter{
            
    private static void createUser(int numOfUser) {
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfUser);
    }

    private static void execute(int run){

    }

    static OutputData out;
    public static void main(String[] args) {
        //Create UserAgents
        createUser(NUMBER_OF_USER);

        //Update Agent
        long start = System.currentTimeMillis();
	System.out.println("Start Agent System : "+start);
        
        //Executable Test
        execute(NUMBER_DATA);
        
        long stop = System.currentTimeMillis();
        System.out.println("Stop Agent System : "+stop);
        
        System.out.println("N="+NUMBER_OF_USER+"D="+NUMBER_DATA+" time : "+(stop-start));
    }
}