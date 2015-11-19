package rda.data;

import rda.queue.MessageObject;

public class ImpulseData implements DataType{
    private static final Long impulse = 1000000L;
    private static final Long period = 60L;
    
    private static Long count;
    private final Data data;
    private final String name;
    private Long limit;

    public ImpulseData() {
        this.name = "ImpulseType";
        this.data = new Data();
        
        //initialise
        data.init();
        count = -1L;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString(){
        Long n = TIME_RUN * 1000 / TIME_PERIOD;
        Long result = (n - (n / period + 1)) * DATA_VOLUME + (n / period + 1) * impulse;
        
        return name + " DataN_" + result;
    }

    @Override
    public MessageObject nextData(Long time) {
        count++;
        
        MessageObject msg = data.getData();
        
        if((time % period) == 0) limit = impulse - 1;
        else limit = DATA_VOLUME.longValue() - 1;
        
        if(count == limit) msg = data.getPoison();
        if(count > limit) {
            msg = null;
            count = -1L;
        }
        
        return msg;
    }
}