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
        
        private Integer size;
        private long wait;
        private Boolean running = true;
        
	public String name;
	public WindowController(int numberOfMQ, int limit, String name, long wait) {
            this.name = name;
            this.size = limit;
            this.wait = wait;
	}
        
        public Boolean pack(MessageObject msg){
            Window w = window.get(msg.id);
            if(w == null) {
                w = new Window(msg.id, size);
                window.put(msg.id, w);
            }
            
            return w.add(msg);
        }
        
        public void send(String id) throws InterruptedException{
            Window w = window.get(id);
            try {
                manager.getMessageQueue(id).putMessage(w.get());
                window.put(id, null);
            } catch (MessageQueueEvent mqev) {
                if(!Thread.currentThread().isInterrupted()) return;
                
                mqev.printEvent();
                Thread.sleep(wait);
            }
        }

	public void sendMessage(MessageObject mes) throws InterruptedException{
            if(window.get(mes.id) == null) window.put(mes.id, new Window(mes.id, size));
            
            if(window.get(mes.id).add(mes)){
                queue.add(window.get(mes.id));
                window.remove(mes.id);
            }
            
            sendMessageQueue();
	}
        
        public Thread sendThread;
        public Queue queue = new ArrayDeque();
	private void sendMessageQueue() throws InterruptedException{
            if(Thread.interrupted()) throw new InterruptedException();
            try {
                Window obj = (Window)queue.poll();
                if(obj != null)
                    manager.getMessageQueue(obj.id).putMessage(obj.get());
            }catch (MessageQueueEvent mqex) {
                mqex.printEvent();
                        
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException ex) {
                }
            }finally{
                if(Thread.interrupted()) throw new InterruptedException();
            }
	}

	public void close(){
            //running = false;
            //sendThread.interrupt();
	}
}