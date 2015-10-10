package rda.data;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;
import rda.queue.MessageObject;
import rda.queue.MessageQueueTimer;

public class MountData implements SetProperty{
    public final String name = "Mount";
    private static long count;
    private final DataGenerator gen;

	public MountData() {
            count = -1;
            
            this.gen = DataGenerator.getInstance();
            gen.init();
	}
 
	public MessageObject getTimeToData(Long t){
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

	private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
        private static final Marker scheduleMaker = MarkerFactory.getMarker("Main Schedule");
	public static void main(String[] args) {
            MountData dataType = new MountData();
            long t = 0L;
            
            MessageObject msg;
            long total = 0;
            for(long i=0; i < TIME_RUN/2; i++){
                while((msg = DATA_TYPE.getTimeToData(i)) != null){
                    total = total + 1;
                }
                logger.print(scheduleMaker, 
                "Experiment Step : {} [{}ms]", new Object[]{i, TIME_PERIOD});
                System.out.println("total_i="+total);
            }
            System.out.println("Total:"+total);
            System.out.println("Data.N:"+getAmountData());
	}
}