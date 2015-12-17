package rda.data.type;

import rda.data.profile.ProfileGenerator;
import rda.data.Data;
import rda.data.DataType;
import static rda.property.SetProperty.DATA_MODE;
import static rda.property.SetProperty.NUMBER_OF_USER_AGENTS;
import rda.queue.obj.MessageObject;

public class MountData implements DataType{
    private final String name;
    private final Data data;

    private static Long count;
    
    private Long time, period, volume;
    
    public MountData(long time, long period, long volume, int numberOfUser, int valueOfUser, int datamode, int profmode) {
        this.name = "MountType";
        this.data = new Data();
        
        this.time = time;
        this.period = period;
        this.volume = volume;
        
        //initialise
        data.init(numberOfUser, valueOfUser, datamode);
        
        //Generate User
        ProfileGenerator prof = ProfileGenerator.getInstance();
        prof.generate(numberOfUser, profmode);
        
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