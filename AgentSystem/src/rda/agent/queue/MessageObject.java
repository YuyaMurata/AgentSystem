package rda.agent.queue;

import rda.agent.template.MessageTemplate;

public class MessageObject extends MessageTemplate{
    public long data;
    
    public MessageObject(String id, String destID, Object data, int sntinel) {
        // TODO 自動生成されたコンストラクター・スタブ
        super(id, destID, sntinel);
        setData(data);
    }
    
    @Override
    public void setData(Object data) {
        this.data = (Long)data;
    }
    
    @Override
    public String toString() {
        return id + "=>" + toID +" ("+data+")";
    }
}
