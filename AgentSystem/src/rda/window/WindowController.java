package rda.window;

import java.util.HashMap;
import java.util.Map;
import rda.agent.queue.MessageObject;

public class WindowController{
    //private static MessageQueueManager manager = MessageQueueManager.getInstance();
    private Map<String, Window> windowMap = new HashMap<>();
    private Integer size;
        
    public WindowController(int limit) {
        this.size = limit;
    }
        
    public Window pack(MessageObject msg){ 
        if(windowMap.get(msg.destID) == null)
            windowMap.put(msg.destID, new Window(msg.destID, size));
        
        return windowMap.get(msg.destID).pack(msg);
    }
    
    public Window get(String id){
        return windowMap.get(id);
    }
    
    public void remove(String id){
        windowMap.remove(id);
    }
}