package rda.queue;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.property.SetProperty;

public class MessageQueueException extends Exception{
	/**
	 *
	 */
	private static final long serialVersionUID = 5686827278265942730L;
	private final String name;
        private static final Marker mqEventMarker = MarkerFactory.getMarker("Message Queue Event");

	public MessageQueueException(String name) {
		// TODO 自動生成されたコンストラクター・スタブ
		super(name);
		this.name = name;
	}

	public void printEvent(){
		SetProperty.logger.info(mqEventMarker, 
                        "MQName_{} : 負荷検知# Time {} [ms]", name, System.currentTimeMillis());

		//イベントのファイル出力
		SetProperty.logger.trace(mqEventMarker, 
                        "MQName_{}:負荷検知#Time {} [ms]", name, System.currentTimeMillis());
	}
}