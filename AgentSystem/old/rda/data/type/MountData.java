package rda.data.type;

import rda.data.Data;
import rda.data.DataType;
import rda.queue.obj.MessageObject;

public class MountData implements DataType{
    private final String name;
    private final Data data;

    private static Long count;
    
    private Long time, period;
    private Integer volume;
    
    public MountData(Long time, Long period, int volume, int numberOfUser, int valueOfUser, int datamode, long seed) {
        this.name = "MountType";
        this.data = new Data();
        
        this.time = time;
        this.period = period;
        this.volume = volume;
        
        //initialise
        data.init(numberOfUser, valueOfUser, datamode, seed);
        
        count = -1L;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString(){
        Long n = time * 1000 / period;
        Long result = n * (n-1) / 2 * volume;
        
        return name + " DataN_" + result;
    }

    @Override
    public MessageObject nextData(Long time) {
        count++;
        
        MessageObject msg = data.getData();
        
        if(count == (time * volume)) msg = data.getPoison();
        if(count > (time * volume)) {
            msg = null;
            count = -1L;
        }
        
        return msg;
    }
}