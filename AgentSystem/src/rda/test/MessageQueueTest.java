package rda.test;

import java.util.ArrayList;

import rda.data.DataGenerator;
import rda.data.OutputData;
import rda.queue.MessageObject;
import rda.queue.ReciveMessageQueue;

public class MessageQueueTest{
	//Experiment Condition

	private static ReciveMessageQueue mq = new ReciveMessageQueue("MQ0");

	private static void send(int run){
            MessageObject msg;
            ArrayList<MessageObject> messageList = new ArrayList<MessageObject>();
            
            for(int i=0; i < run; i++){
		DataGenerator ag = DataGenerator.getInstance();
                msg = new MessageObject(ag.getData().agentKey, ag.getData().data);
		
		messageList.add(msg);

		//sendMessageQueue(mq, message);
            }
            
            System.out.println(messageList.toString());
    }

	private static void sendMessageQueue(ReciveMessageQueue messageQueue, Object message){
		//QueueにPutする
		messageQueue.putMessage(message);
	}

	static OutputData out;
	public static void main(String[] args) {
            //Update Agent
            int number = 100;
            if(!args.equals(""))
                number = Integer.parseInt(args[0]);
            send(number);
	}
}