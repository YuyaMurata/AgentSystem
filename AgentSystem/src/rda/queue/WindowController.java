package rda.queue;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import rda.data.OutputData;
import rda.property.SetPropertry;
import test.CalcUsage;

public class WindowController extends SetPropertry{
	private ArrayList<ReciveMessageQueue> mq = new ArrayList<>();
	private ArrayList<MessageObject> window[] = new ArrayList[NUMBER_OF_QUEUE];

	private static OutputData out;

	public String name;
	public WindowController(String name) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.name = name;

		for(int i=0; i < NUMBER_OF_QUEUE; i++){
			this.mq.add(new ReciveMessageQueue("RMQ"+i));
			window[i] = new ArrayList<>();
		}

		out = new OutputData("MQ"+NUMBER_OF_QUEUE+"_"+System.currentTimeMillis()+".csv");

	}

	public void sendMessage(MessageObject mes){
		if(mes.data != -1) add(HashToMQN.toMQN(mes.agentKey), mes);

		check(mes.data, window);
	}

	private void add(int i, MessageObject mes){
		window[i].add(mes);

		check(i, window[i]);
	}

	private void check(int i, ArrayList<MessageObject> list){
		if(list.size() > WINDOW_SIZE){
			sendMessageQueue(i, list.clone());
			list.clear();
		}
	}

	private void check(int s, ArrayList<MessageObject>[] array){
		if(s == -1){
			for(int i=0; i < array.length; i++)
				if(!array[i].isEmpty()){
					sendMessageQueue(i, array[i].clone());
					array[i].clear();
				}
		}
	}

	private void sendMessageQueue(int i, Object mes){
		//QueueにPutする
		mq.get(i).putMessage(mes);
	}

	public void close(){
		for(int i=0; i < mq.size(); i++){
                    try {
                        mq.get(i).close().join();
                    } catch (InterruptedException ex) {
                        System.out.println(ex);
                    }
		}

		out.close();
	}

	private CalcUsage getCPULoad = new CalcUsage();
	public  void outputMQLog(int t){
		StringBuilder sb = new StringBuilder(t+","+getCPULoad.getUsage());

		for(int i=0; i < mq.size(); i++){
			sb.append(",");
			sb.append(mq.get(i).getSize());
		}

		out.write(sb.toString());

	}
}