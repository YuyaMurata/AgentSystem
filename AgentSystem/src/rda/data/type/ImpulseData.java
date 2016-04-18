package rda.data.type;

import rda.agent.queue.MessageObject;
import rda.data.Data;
import rda.data.DataType;

public class ImpulseData implements DataType{
    private static final Long impulse = 1000000L;
    private static final Long timing = 60L;
    
    private static Long count;
    private final Data data;
    private final String name;
    private Long limit;
    
    private Long time, period, volume;

    public ImpulseData(long time, long period, long volume, int numberOfUser, int valueOfUser, int datamode, long seed) {
        this.name = "ImpulseType";
        this.data = new Data();
        
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
        Long result = (n - (n / timing + 1)) * volume + (n / timing + 1) * impulse;
        
        return name + " DataN_" + result;
    }

    @Override
    public MessageObject nextData(Long time) {
        count++;
        
        MessageObject msg = data.getData();
        
        if((time % timing) == 0) limit = impulse - 1;
        else limit = volume - 1;
        
        if(count == limit) msg = data.getPoison();
        if(count > limit) {
            msg = null;
            count = -1L;
        }
        
        return msg;
    }

    @Override
    public String toString(Long time) {
        if((time % timing) == 0) return String.valueOf(impulse - 1);
        else return String.valueOf(volume - 1);
    }
}