package rda.data;

import rda.property.SetProperty;
import rda.queue.MessageObject;

public class MountData extends SetProperty{
    public static final String name = "Mount";
    private static Integer count;

	public MountData() {
            count = -1;
	}
 
	public MessageObject getTimeToData(int t){
            DataGenerator gen = DataGenerator.getInstance();
		count++;

		if(count < t*VOLUME) return gen.getData();
		else if(count == t*VOLUME) 
                    return new MessageObject(gen.getData().agentKey, -1);
		else{
			count = -1;
			return null;
		}
	}
        
        public Long getAmountData(){
            Long n = (TIME_RUN / TIME_PERIOD) + 1; 
            return n * (n-1) / 2 * VOLUME;
        }

	/**
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
	}**/

}