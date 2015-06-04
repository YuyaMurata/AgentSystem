package rda.test;

import java.util.ArrayList;

import rda.data.DataGenerator;
import rda.data.OutputData;
import rda.property.SetPropertry;
import rda.queue.MessageObject;
import rda.queue.MessageQueueException;
import rda.queue.ReciveMessageQueue;

public class MessageQueueTest extends SetPropertry{
	//Experiment Condition

	private static ReciveMessageQueue mq = new ReciveMessageQueue("MQ0");

	//Agent Define
	public static final String AGENT_TYPE = "useragent";

	private static void send(int run){
		for(int i=0; i < run; i++){
			DataGenerator ag = DataGenerator.getInstance();

			ArrayList<MessageObject> message = new ArrayList<MessageObject>();
			message.add(new MessageObject(ag.getData().agentKey, ag.getData().data));

			sendMessageQueue(mq, message);
		}
	}

	private static void sendMessageQueue(ReciveMessageQueue messageQueue, Object message){
		//QueueにPutする
		messageQueue.putMessage(message);
	}

	static OutputData out;
	public static void main(String[] args) {
		//Update Agent
		send(TIME_RUN);
	}
}