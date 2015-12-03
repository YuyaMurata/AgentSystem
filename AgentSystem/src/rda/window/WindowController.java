package rda.window;

import com.ibm.agent.exa.AgentKey;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import rda.queue.MessageObject;
import rda.queue.MessageQueueException;
import rda.queue.MessageQueueManager;

public class WindowController{
        private MessageQueueManager manager = MessageQueueManager.getInstance();
	private HashMap<AgentKey, Window> window = new HashMap<>();
        public Queue queue = new ArrayDeque();
        private final Integer size;
        
	public String name;
	public WindowController(int numberOfMQ, int limit, String name) {
            this.name = name;
            this.size = limit;
	}

	public void sendMessage(MessageObject mes){
            if(window.get(mes.agentKey) == null) window.put(mes.agentKey, new Window(mes.agentKey, size));
            
            if(window.get(mes.agentKey).add(mes)){
                queue.add(window.get(mes.agentKey).clone());
                window.remove(mes.agentKey);
            }
                
            sendMessageQueue();
	}

	private void sendMessageQueue(){
            Window obj = (Window) queue.poll();
            if(obj != null)
                try {
                    manager.getMessageQueue(obj.key).putMessage(obj.get());
                } catch (InterruptedException ex) {
                } catch (MessageQueueException mqex) {
                    mqex.printEvent();
                    queue.add(obj);
                }
	}

	public void close(){
            manager.closeAll();
	}
}