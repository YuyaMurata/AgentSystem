package rda.queue;

import java.util.ArrayList;

import rda.data.OutputData;
import rda.property.SetPropertry;
import test.CalcUsage;

public class WindowController extends SetPropertry{
	private ReciveMessageQueue[] mqArray = new ReciveMessageQueue[NUMBER_OF_QUEUE];
	private ArrayList<MessageObject> window[] = new ArrayList[NUMBER_OF_QUEUE];

	private static OutputData out;

        private void init(){
            for(int i=0; i < NUMBER_OF_QUEUE; i++){
                this.mqArray[i] = new ReciveMessageQueue("RMQ"+i);
                window[i] = new ArrayList<>();
            }

            out = new OutputData("MQ"+NUMBER_OF_QUEUE+"_"+System.currentTimeMillis()+".csv");
        }
        
	public String name;
	public WindowController(String name) {
            // TODO 自動生成されたコンストラクター・スタブ
            this.name = name;
            init();
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
		mqArray[i].putMessage(mes);
	}

	public void close(){
            for (ReciveMessageQueue mq : mqArray) {
                try {
                    mq.close().join();
                }catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }

		out.close();
	}

	private CalcUsage getCPULoad = new CalcUsage();
	public  void outputMQLog(int t){
		StringBuilder sb = new StringBuilder(t+","+getCPULoad.getUsage());

            for (ReciveMessageQueue mq : mqArray) {
                sb.append(",");
                sb.append(mq.getSize());
            }

            out.write(sb.toString());

	}
}