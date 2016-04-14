package rda.window;

import java.util.HashMap;
import java.util.Map;
import rda.queue.obj.MessageObject;

public class WindowController{
    //private static MessageQueueManager manager = MessageQueueManager.getInstance();
    private Map<String, Window> windowMap = new HashMap<>();
    private Integer size;
        
    public WindowController(int limit) {
        this.size = limit;
    }
        
    public Window pack(MessageObject msg){
        //System.out.println("WINDOW_CTL : 1 "+ windowMap + "[" +msg.toString()+"]");
        
        if(windowMap.get(msg.destID) == null)
            windowMap.put(msg.destID, new Window(msg.destID, size));
        
        //System.out.println("WINDOW_CTL : 2 "+ windowMap + "[" +msg.toString()+"]");
        
        return windowMap.get(msg.destID).pack(msg);
    }
    
    public Window get(String id){
        return windowMap.get(id);
    }
    
    public void remove(String id){
        windowMap.remove(id);
    }
        
    /*public void send(String id) throws InterruptedException{
        Window w = window.get(id);
        try {
            manager.getMessageQueue(id).putMessage(w.unpack());
            window.put(id, null);
        } catch (MessageQueueEvent mqev) {
            if(Thread.currentThread().isInterrupted()) return ;
            
            mqev.printEvent();
            //Thread.sleep(wait);
        }
    }*/
}