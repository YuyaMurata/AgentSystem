package rda.queue;

import java.util.ArrayList;

import rda.property.SetProperty;
import test.CalcUsage;

public class WindowController extends SetProperty{
	private ReciveMessageQueue[] mqArray;
	private ArrayList<MessageObject>[] window; 
        public Boolean running;

        private void init(int numberOfMQ){
            mqArray = new ReciveMessageQueue[numberOfMQ];
            window = new ArrayList[numberOfMQ];
            
            running = true;
            
            for(int i=0; i < numberOfMQ; i++){
                this.mqArray[i] = new ReciveMessageQueue("RMQ"+i, this);
                window[i] = new ArrayList<>();
            }
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
            running = false;
	}

        /**
	private final CalcUsage getCPULoad = new CalcUsage();
	public  void outputMQLog(int t){
            StringBuilder sb = new StringBuilder(t);

            for (ReciveMessageQueue mq : mqArray)
                sb.append(mq.getSize());
            
            logger.trace(name);
	}
        * **/
}