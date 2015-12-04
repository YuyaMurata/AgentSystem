package rda.queue.obj;

public class MessageObject {

    public String id;
    public int data;

    public MessageObject(String id, int data) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.id = id;
        this.data = data;
    }
	
    @Override
    public String toString(){
        return id+","+data;
    }
}
