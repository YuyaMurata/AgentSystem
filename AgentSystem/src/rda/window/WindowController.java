package rda.window;

import java.util.ArrayList;
import rda.queue.HashToMQN;
import rda.queue.MessageObject;
import rda.queue.reciver.ReciveMessageQueue;

public class WindowController{
	private ReciveMessageQueue[] mqArray;
	private ArrayList<MessageObject>[] window; 
        private final Integer size;
        
        private void init(int numberOfMQ){
            this.mqArray = new ReciveMessageQueue[numberOfMQ];
            this.window = new ArrayList[numberOfMQ];
                        
            for(int i=0; i < numberOfMQ; i++){
                this.mqArray[i] = new ReciveMessageQueue("RMQ"+i);
                this.window[i] = new ArrayList<>();
            }
            
            for(ReciveMessageQueue mq : mqArray)
                mq.start();
        }
        
	public String name;
	public WindowController(int numberOfMQ, int limit, String name) {
            this.name = name;
            this.size = limit;
            
            init(numberOfMQ);
	}

	public void sendMessage(MessageObject mes){
            int no = HashToMQN.toMQN(mes.agentKey);
            if(mes.data != -1) this.window[no].add(mes);
            
            if((mes.data == -1) || (this.window[no].size() == size)){
                sendMessageQueue(no, this.window[no].clone());
                this.window[no].clear();
            }
	}

	private void sendMessageQueue(int i, Object mes){
            try {
                this.mqArray[i].putMessage(mes);
            } catch (InterruptedException ex) {
            }
	}

	public void close(){
            for(ReciveMessageQueue mq : mqArray)
                mq.stop();
	}
}