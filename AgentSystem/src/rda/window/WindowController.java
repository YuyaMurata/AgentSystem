package rda.window;

import java.util.ArrayList;
import java.util.HashMap;
import rda.queue.obj.MessageObject;
import rda.queue.event.MessageQueueEvent;
import rda.queue.manager.MessageQueueManager;

public class WindowController{
    private static MessageQueueManager manager = MessageQueueManager.getInstance();
    private HashMap<String, Window> window = new HashMap<>();
        
    private Integer size;
    private long wait;
        
    public WindowController(int limit, long wait) {
        this.size = limit;
        this.wait = wait;
    }
        
    public Window pack(MessageObject msg){
        Window w = window.get(msg.destID);
        if(w == null) {
            w = new Window(msg.destID, size);
            window.put(msg.destID, w);
        }
            
        return w.pack(msg);
    }
    
    public Window get(String id){
        return window.get(id);
    }
    
    public void remove(String id){
        window.put(id, null);
    }
        
    public void send(String id) throws InterruptedException{
        Window w = window.get(id);
        try {
            manager.getMessageQueue(id).putMessage(w.unpack());
            window.put(id, null);
        } catch (MessageQueueEvent mqev) {
            if(Thread.currentThread().isInterrupted()) return ;
            
            mqev.printEvent();
            //Thread.sleep(wait);
        }
    }
}