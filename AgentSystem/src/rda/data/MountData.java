package rda.data;

import rda.property.SetProperty;
import rda.queue.MessageObject;

public class MountData implements SetProperty{
    public final String name = "Mount";
    private static long count;
    private final DataGenerator gen;

	public MountData() {
            count = -1;
            
            this.gen = DataGenerator.getInstance();
            gen.init();
	}
 
	public MessageObject getTimeToData(long t){
            count++;

            if(count <= t*DATA_VOLUME) return gen.getData();
            else if(count == t * DATA_VOLUME + 1) 
                return new MessageObject(gen.getData().agentKey, -1);
            else{
                count = -1;
                return null;
            }
	}
        
        public static Long getAmountData(){
            Long n = TIME_RUN * 1000 / TIME_PERIOD;
            Long result = n * (n-1) / 2 * DATA_VOLUME;
            return result;
        }

	/**
	public static void main(String[] args) {
            MountData dataType = new MountData();
            
            MessageObject msg;
            long total = 0;
            for(int i=0; i < TIME_RUN*10; i++){
                while((msg = DATA_TYPE.getTimeToData(i)) != null){
                    total = total + msg.data;
                }
            }
            System.out.println("Total:"+total);
            
            //Total:17969999400
            System.out.println("Data.N:"+getAmountData());
	}
        */
}