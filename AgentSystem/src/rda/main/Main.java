package rda.main;

import rda.agent.CreateAgent;
import rda.data.MountData;
import rda.property.SetPropertry;
import rda.queue.MessageObject;
import rda.queue.WindowController;

public class Main extends SetPropertry{
	private static WindowController mq;
	private static void init(){
		//File
		out.write("MessageQueue_Event Time: Agent_"+NUMBER_OF_USER_AGENTS+", Run_"+TIME_RUN);
		out.write("MessageQueue Property: Window Size_"+WINDOW_SIZE+", MQ Length_"+QUEUE_LENGTH+", wait_"+QUEUE_WAIT);

		mq = new WindowController(String.valueOf("Win_Main"));
	}

	private static void createUser(int numOfAgents){
		CreateAgent agent = new CreateAgent();
		agent.create("U#00", numOfAgents);
	}

	private static final MountData mt = new MountData();
	private static void sendMessage(int t){
		MessageObject message = null;
		while((message = mt.getTimeToData(t)) != null){
			mq.sendMessage(message);
		}
	}

	public static void main(String[] args) {
		//initialize
		init();

		//Agentの生成
		createUser(NUMBER_OF_USER_AGENTS);

		//Execute Agent System
		long start = System.currentTimeMillis();
		System.out.println("Start Agent System : "+start);

		execute();

		long stop = System.currentTimeMillis();
		System.out.println("Stop Agent System : "+stop);

		// System Log
		System.out.println("transaction_time : "+(stop-start));
		out.write("start_time:"+start+",stop_time:"+stop+","+(stop-start)+",[ms]");

		out.close();
	}

	private static void execute(){
		try {
			for(int i=0; i < TIME_RUN; i++){
				System.out.println("Experiment Step :" + i +" sec");


				Thread.sleep(TIME_PERIOD);


				//Output Queue Length
				mq.outputMQLog(i);

				sendMessage(i+1);
			}

			mq.close();

		} catch (InterruptedException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
        }
}