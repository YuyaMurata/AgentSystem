package rda.test;

import java.util.ArrayList;
import rda.agent.CreateAgent;

import rda.data.OutputData;
import rda.queue.MessageObject;
import rda.window.WindowController;

public class MessageQueueTest extends TestParameter{
            
    private static void createUser(int numOfUser) {
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfUser);
    }

    private static void execute(int run){
        //ReciveMessageQueue rmq = new ReciveMessageQueue("MQ0");
        WindowController mq = new WindowController(NUMBER_OF_QUEUE, WINDOW_SIZE, "DataWinow");
        
        MessageObject msg = null;
        ArrayList<MessageObject> oneMessage;
        
        for(int i=0; i < run; i++){
            
            //WindowContoroler to MQ
            mq.sendMessage(msg);
            
            // Direct MessageQueue
            /*oneMessage = new ArrayList<MessageObject>();
            oneMessage.add(msg);
            rmq.putMessage(oneMessage);*/
        }
        
        //WindowContoroler  Data End
        mq.sendMessage(new MessageObject(msg.agentKey, -1));
        
        mq.close();
        
        /*try {
            rmq.close().join();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }*/
    }

    static OutputData out;
    public static void main(String[] args) {
        //Create UserAgents
        createUser(NUMBER_OF_USER_AGENTS);

        //Update Agent
        long start = System.currentTimeMillis();
	System.out.println("Start Agent System : "+start);
        
        //Executable Test
        execute(NUMBER_DATA);
        
        long stop = System.currentTimeMillis();
        System.out.println("Stop Agent System : "+stop);
        
        System.out.println("N="+NUMBER_OF_USER_AGENTS+"D="+NUMBER_DATA+" time : "+(stop-start));
    }
}