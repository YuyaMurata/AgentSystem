package rda.queue;

import java.util.ArrayList;

public class WindowController{
	private ReciveMessageQueue[] mqArray;
	private ArrayList<MessageObject>[] window; 
        private final Integer size;
        public Boolean running;

        private void init(int numberOfMQ){
            this.mqArray = new ReciveMessageQueue[numberOfMQ];
            this.window = new ArrayList[numberOfMQ];
            
            running = true;
            
            for(int i=0; i < numberOfMQ; i++){
                this.mqArray[i] = new ReciveMessageQueue("RMQ"+i, this);
                this.window[i] = new ArrayList<>();
            }
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
            this.mqArray[i].putMessage(mes);
	}

	public void close(){
            running = false;
            for(ReciveMessageQueue mq : mqArray)
                mq.isFinish();
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