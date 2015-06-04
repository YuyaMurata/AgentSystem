package rda.data;

import rda.property.SetPropertry;
import rda.queue.MessageObject;

public class MountData extends SetPropertry{
	private static final Integer VOLUME = 10;
	private static Integer count;

	public MountData() {
		// TODO 自動生成されたコンストラクター・スタブ
		count = -1;
	}

	private static DataGenerator gen = DataGenerator.getInstance();

	public MessageObject getTimeToData(int t){
		count++;

		if(count < t*VOLUME) return gen.getData();
		else if(count == t*VOLUME) return new MessageObject(null, -1);
		else{
			count = -1;
			return null;
		}
	}

	
	public static void main(String[] args) {
		MountData mt = new MountData();

		int cnt =0;
		for(int i=0; i < 2000; i++){
			long start = System.currentTimeMillis();

			MessageObject mes;
	
			while((mes = mt.getTimeToData(i+1)) != null) cnt++;//System.out.println("UserID:"+mes.toString());

			System.out.println(cnt);

			long stop = System.currentTimeMillis();

			System.out.println("Time: " + (stop-start) +" ms");
		}
	}

}