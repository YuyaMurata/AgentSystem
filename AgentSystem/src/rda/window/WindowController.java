package rda.window;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.queue.obj.MessageObject;
import rda.queue.event.MessageQueueEvent;
import rda.queue.manager.MessageQueueManager;

public class WindowController{
        private MessageQueueManager manager = MessageQueueManager.getInstance();
	private HashMap<String, Window> window = new HashMap<>();
        public BlockingQueue queue = new ArrayBlockingQueue(100000);
        private final Integer size;
        
	public String name;
	public WindowController(int numberOfMQ, int limit, String name) {
            this.name = name;
            this.size = limit;
	}

	public void sendMessage(MessageObject mes){
            if(window.get(mes.id) == null) window.put(mes.id, new Window(mes.id, size));
            
            if(window.get(mes.id).add(mes)){
                queue.add(window.get(mes.id).clone());
                window.remove(mes.id);
            }
                
            sendMessageQueue();
	}

	private void sendMessageQueue(){
            Window obj = (Window) queue.poll();
            if(obj != null)
                try {
                    manager.getMessageQueue(obj.id).putMessage(obj.get());
                } catch (InterruptedException ex) {
                } catch (MessageQueueEvent mqex) {
                    mqex.printEvent();
                    
                    //Return Data (*effect latency)
                    queue.add(obj);
                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
                }
	}

	public void close(){
            manager.stopAll();
	}
}