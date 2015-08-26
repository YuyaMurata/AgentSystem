package rda.test;

import java.util.ArrayList;
import rda.agent.CreateAgent;

import rda.data.DataGenerator;
import rda.data.OutputData;
import rda.queue.MessageObject;
import rda.queue.WindowController;

public class MessageQueueTest{
    //Experiment Condition
    private static final int NUMBER_USER = 1;
    private static final int NUMBER_DATA = 1000;
            
    private static void createUser(int numOfUser) {
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfUser);
    }

    private static void execute(int run){
        //ReciveMessageQueue rmq = new ReciveMessageQueue("MQ0");
        WindowController mq = new WindowController(String.valueOf("Win_Main"));
        
        MessageObject msg;
        ArrayList<MessageObject> messageList = new ArrayList<MessageObject>();
        ArrayList<MessageObject> oneMessage;
        DataGenerator ag = DataGenerator.getInstance();
        
        for(int i=0; i < run; i++){
            msg = new MessageObject(ag.getData().agentKey, ag.getData().data);
            
            //WindowContoroler to MQ
            mq.sendMessage(msg);
            
            // Direct MessageQueue
            //oneMessage = new ArrayList<MessageObject>();
            //rmq.putMessage(oneMessage.add(msg));
            
        }
        
        mq.close();
        //rmq.close();
            
        System.out.println(messageList.toString());
    }

    static OutputData out;
    public static void main(String[] args) {
        //Create UserAgents
        createUser(NUMBER_USER);

        //Update Agent
        long start = System.currentTimeMillis();
	System.out.println("Start Agent System : "+start);
        
        //Executable Test
        execute(NUMBER_DATA);
        
        long stop = System.currentTimeMillis();
        System.out.println("Stop Agent System : "+stop);
        
        System.out.println("N="+NUMBER_USER+"D="+NUMBER_DATA+" time : "+(stop-start));
    }
}