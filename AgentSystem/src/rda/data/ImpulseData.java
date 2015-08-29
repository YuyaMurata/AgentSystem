package rda.data;

import java.util.ArrayList;

import rda.property.SetProperty;
import rda.queue.MessageObject;

public class ImpulseData implements SetProperty{
	private static final Integer LIMIT = 1000;
	private static ArrayList<Integer> timeRecord = new ArrayList<Integer>();
	private static Integer timer;

	public ImpulseData() {
		// TODO 自動生成されたコンストラクター・スタブ
		timer = -1;
		timeRecordGenerator();
	}

	private static void timeRecordGenerator(){
		for(int i=1; i < TIME_RUN+1; i++){
			if(i % 60 == 0) timeRecord.add(LIMIT);
			else timeRecord.add(1);
		}
	}

	private static DataGenerator gen = DataGenerator.getInstance();
	public ArrayList<MessageObject> getList(){
		timer++;
		ArrayList<MessageObject> data = new ArrayList<MessageObject>();
		for(int i=0; i < timeRecord.get(timer); i++)
			data.add(gen.getData());

		return data;
	}
}