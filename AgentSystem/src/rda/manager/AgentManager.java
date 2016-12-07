package rda.manager;

import java.util.Map;
import rda.agent.queue.QueueObserver;
import rda.window.WindowController;


public abstract class AgentManager {
    public abstract void add(QueueObserver observe);
    public abstract Boolean getState();
    public abstract Boolean getAutoMode();
    public abstract IDManager getIDManager();
    public abstract WindowController getWindowController();
    public abstract String createCloneAgent(String id, Object state);
    public abstract Integer getNumAgents();
    public abstract Map getMQMap();
}
