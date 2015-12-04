package rda.data;

import rda.queue.obj.MessageObject;

public class MountData implements DataType{
    private static Long count;
    private final Data data;
    private final String name;

    public MountData() {
        this.name = "MountType";
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
        Long result = n * (n-1) / 2 * DATA_VOLUME;
        
        return name + " DataN_" + result;
    }

    @Override
    public MessageObject nextData(Long time) {
        count++;
        
        MessageObject msg = data.getData();
        
        if(count == (time * DATA_VOLUME)) msg = data.getPoison();
        if(count > (time * DATA_VOLUME)) {
            msg = null;
            count = -1L;
        }
        
        return msg;
    }
}