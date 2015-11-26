package rda.window;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import rda.queue.IDToMQN;
import rda.queue.MessageObject;
import rda.queue.MessageQueueException;
import rda.queue.log.MQSpecificStorage;
import rda.queue.reciver.ReciveMessageQueue;

public class WindowController{
	private ReciveMessageQueue[] mqArray;
	private HashMap<Integer, Window> window = new HashMap<>();
        public Queue queue = new ArrayDeque();
        private final Integer size;
        private IDToMQN id = IDToMQN.getInstance();
        
        private void init(int numberOfMQ){
            this.mqArray = new ReciveMessageQueue[numberOfMQ];
                        
            for(int i=0; i < numberOfMQ; i++){
                this.mqArray[i] = new ReciveMessageQueue("RMQ"+i);
            }
            
            MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
            mqSS.storeMessageQueue(mqArray);
            
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
            int no = id.toMQN(mes.agentKey);
            if(window.get(no) == null) window.put(no, new Window(no, size));
            
            if(window.get(no).add(mes)){
                queue.add(window.get(no).clone());
                window.remove(no);
            }
                
            sendMessageQueue();
	}

	private void sendMessageQueue(){
            Window obj = (Window) queue.poll();
            if(obj != null)
                try {
                    mqArray[obj.id].putMessage(obj.get());
                } catch (InterruptedException ex) {
                } catch (MessageQueueException mqex) {
                    mqex.printEvent();
                    queue.add(obj);
                }
	}

	public void close(){
            for(ReciveMessageQueue mq : mqArray)
                mq.stop();
	}
}