package rda.queue.obj;

public class MessageObject {

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
