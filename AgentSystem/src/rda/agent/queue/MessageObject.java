package rda.agent.queue;

import rda.queue.obj.*;
import java.io.Serializable;

public class MessageObject implements  Serializable{

    public String id, destID;
    public int data;

    public MessageObject(String id, int data, String destID) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.id = id;
        this.data = data;
        this.destID = destID;
    }
	
    @Override
    public String toString(){
        return id+","+data+"->"+destID;
    }
}
