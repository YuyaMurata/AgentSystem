package rda.queue;

import java.util.ArrayList;

import rda.data.OutputData;
import rda.property.SetPropertry;
import test.CalcUsage;

public class WindowController extends SetPropertry{
	private final ReciveMessageQueue[] mqArray = new ReciveMessageQueue[NUMBER_OF_QUEUE];
	private final ArrayList<MessageObject> window[] = new ArrayList[NUMBER_OF_QUEUE];

	private static OutputData outdata;

        private void init(){
            for(int i=0; i < NUMBER_OF_QUEUE; i++){
                this.mqArray[i] = new ReciveMessageQueue("RMQ"+i);
                window[i] = new ArrayList<>();
            }

            outdata = new OutputData("MQ"+NUMBER_OF_QUEUE+"_"+System.currentTimeMillis()+".csv");
        }
        
	public String name;
	public WindowController(String name) {
            // TODO 自動生成されたコンストラクター・スタブ
            this.name = name;
            init();
	}

	public void sendMessage(MessageObject mes){
            int no = HashToMQN.toMQN(mes.agentKey);
            if(mes.data != -1) window[no].add(mes);
            
            if((mes.data == -1) || (window[no].size() == WINDOW_SIZE)){
                sendMessageQueue(no, window[no].clone());
                window[no].clear();
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

            outdata.close();
	}

	private final CalcUsage getCPULoad = new CalcUsage();
	public  void outputMQLog(int t){
		StringBuilder sb = new StringBuilder(t+","+getCPULoad.getUsage());

            for (ReciveMessageQueue mq : mqArray) {
                sb.append(",");
                sb.append(mq.getSize());
            }

            outdata.write(sb.toString());

	}
}