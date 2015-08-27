package rda.queue;

import java.util.ArrayList;

import rda.data.OutputData;
import rda.property.SetPropertry;
import test.CalcUsage;

public class WindowController extends SetPropertry{
	private ReciveMessageQueue[] mqArray;
	private ArrayList<MessageObject>[] window; 

	private static OutputData outdata;

        private void init(int numberOfMQ){
            mqArray = new ReciveMessageQueue[numberOfMQ];
            window = new ArrayList[numberOfMQ];
            
            for(int i=0; i < numberOfMQ; i++){
                this.mqArray[i] = new ReciveMessageQueue("RMQ"+i);
                window[i] = new ArrayList<>();
            }

            outdata = new OutputData("QueueLength_MQ"+numberOfMQ+"_"+System.currentTimeMillis()+".csv");
        }
        
	public String name;
	public WindowController(int numberOfMQ, String name) {
            // TODO 自動生成されたコンストラクター・スタブ
            this.name = name;
            init(numberOfMQ);
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