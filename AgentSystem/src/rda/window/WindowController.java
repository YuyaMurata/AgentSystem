package rda.window;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import rda.queue.obj.MessageObject;
import rda.queue.event.MessageQueueEvent;
import rda.queue.manager.MessageQueueManager;

public class WindowController{
    private static MessageQueueManager manager = MessageQueueManager.getInstance();
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
            if(Thread.currentThread().isInterrupted()) return;
            
            mqev.printEvent();
            //Thread.sleep(wait);
        }
    }
}