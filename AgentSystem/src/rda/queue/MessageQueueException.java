package rda.queue;

import rda.property.SetPropertry;

public class MessageQueueException extends Exception{
	/**
	 *
	 */
	private static final long serialVersionUID = 5686827278265942730L;
	private String name;

	public MessageQueueException(String name) {
		// TODO 自動生成されたコンストラクター・スタブ
		super(name+":MessageQueue 負荷検知#"+System.currentTimeMillis()+" [ms]");
		this.name = name;
	}

	public void printEvent(){
		System.out.println(name+" : 負荷検知#"+System.currentTimeMillis()+" [ms]");

		//イベントのファイル出力
		SetPropertry.out.write(name + ","+String.valueOf(System.currentTimeMillis()));
	}
}