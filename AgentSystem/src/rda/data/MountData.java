package rda.data;

import rda.property.SetProperty;
import rda.queue.MessageObject;

public class MountData implements SetProperty{
    public final String name = "Mount";
    private static long count;
    private final DataGenerator gen;

    public MountData() {
        this.gen = DataGenerator.getInstance();
        gen.init();
    }
 
    public MessageObject getTimeToData(Long t){
        count++;

        if(count <= t * DATA_VOLUME){
            if(count == t * DATA_VOLUME) 
                return new MessageObject(gen.getData().agentKey, -1);
            return gen.getData();
        } else{
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
        Long total = 0L;
        
        MessageObject msg;
        for(long t=0; t < TIME_RUN; t++){
            while((msg = DATA_TYPE.getTimeToData(t)) != null){
                //mq.sendMessage(msg);
                if(msg.data != -1)
                    total = total + msg.data;
            }
            System.out.println("Time_"+t+" Total:"+total);
        }
        
        System.out.println("Estimate Total:"+getAmountData());
    }*/
}