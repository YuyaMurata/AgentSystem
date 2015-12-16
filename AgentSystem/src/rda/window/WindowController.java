package rda.window;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import rda.queue.obj.MessageObject;
import rda.queue.event.MessageQueueEvent;
import rda.queue.manager.MessageQueueManager;

public class WindowController{
        private MessageQueueManager manager = MessageQueueManager.getInstance();
	private HashMap<String, Window> window = new HashMap<>();
        
        private final Integer size;
        private long wait;
        
	public String name;
	public WindowController(int numberOfMQ, int limit, String name, long wait) {
            this.name = name;
            this.size = limit;
            this.wait = wait;
	}

	public void sendMessage(MessageObject mes){
            if(window.get(mes.id) == null) window.put(mes.id, new Window(mes.id, size));
            
            if(window.get(mes.id).add(mes)){
                sendMessageQueue(window.get(mes.id).clone());
                window.remove(mes.id);
            }
	}

        public Queue queue = new ArrayDeque();
	private void sendMessageQueue(Object win){
            Window obj = (Window)win;// queue.poll();
            if(obj != null)
                try {
                    manager.getMessageQueue(obj.id).putMessage(obj.get());
                } catch (InterruptedException e) {
                } catch (MessageQueueEvent mqex) {
                    mqex.printEvent();
                    
                    //Return Data (*effect latency)
                    queue.add(obj);
                    
                    try {
                        Thread.sleep(wait);
                    } catch (InterruptedException ex) {
                    }
                }
	}

	public void close(){
            manager.stopAll();
	}
}