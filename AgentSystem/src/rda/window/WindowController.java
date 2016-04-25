package rda.window;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import rda.agent.queue.MessageObject;

public class WindowController{
    private ExecutorService aliveThread;
    private Map<String, Window> windowMap = new HashMap<>();
    private Queue executableQueue = new ConcurrentLinkedQueue<>();
    private Integer size;
    private Long aliveTime;
        
    public WindowController(int limit, Long aliveTime, int poolsize) {
        this.size = limit;
        this.aliveThread = Executors.newFixedThreadPool(poolsize);
        this.aliveTime = aliveTime;
    }
        
    public void pack(MessageObject msg){
        if(windowMap.get(msg.destID) == null)
            windowMap.put(msg.destID, new Window(this, msg.destID, size));
        
        windowMap.get(msg.destID).pack(msg);
    }
    
    public void addExecutable(Window window){
        executableQueue.add(window);
    }
    
    public Window get(){
        Window window = (Window)executableQueue.poll();
        if(window != null) System.out.println(">>WindowContoroller"+check(window.getOrigID()));
        return window;
    }
    
    public void remove(String id){
        windowMap.remove(id);
    }
    
    public boolean check(String id){
        return windowMap.get(id) != null;
    }
}