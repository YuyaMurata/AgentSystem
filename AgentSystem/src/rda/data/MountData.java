package rda.data;

import rda.property.SetProperty;
import rda.queue.MessageObject;

public class MountData implements SetProperty{
    public final String name = "Mount";
    private static Integer count;
    private final DataGenerator gen;

	public MountData() {
            count = -1;
            
            this.gen = DataGenerator.getInstance();
            gen.init();
	}
 
	public MessageObject getTimeToData(int t){
		count++;

		if(count < t*DATA_VOLUME) return gen.getData();
		else if(count == t*DATA_VOLUME) 
                    return new MessageObject(gen.getData().agentKey, -1);
		else{
			count = -1;
			return null;
		}
	}
        
        public Long getAmountData(){
            Long n = (TIME_RUN*1000 / TIME_PERIOD) + 1;
            Long result = n * (n-1) / 2 * DATA_VOLUME;
            return result;
        }

	/**
	public static void main(String[] args) {
            MountData dataType = new MountData();
            
            MessageObject msg;
            HashMap<AgentKey, Integer> count = new HashMap<>();
            for(int i=0; i < TIME_RUN; i++)
            while((msg = DATA_TYPE.getTimeToData(i)) != null){
                int cnt = 0;
                
                if(msg.data != -1){
                    if(count.get(msg.agentKey) != null)
                        cnt = count.get(msg.agentKey) + msg.data;
                    count.put(msg.agentKey , cnt);
                }
            }
            
            for(AgentKey key : count.keySet())
                System.out.println("AgentKey_"+key+" MQ_"+HashToMQN.toMQN(key)+" Count_"+count.get(key));
	}
        **/
}