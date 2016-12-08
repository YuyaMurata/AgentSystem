package rda.agent.queue;

import rda.agent.template.MessageTemplate;

public class MessageObject extends MessageTemplate{
    public String id;
    public Object data;
    
    public MessageObject(String destID, Object data) {
        super();
        this.id = destID;
        this.data = data;
    }
    
    @Override
    public String toString() {
        return id + ": datasize=" + data;
    }
}