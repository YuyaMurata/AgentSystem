/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data.type;

import rda.agent.queue.MessageObject;
import rda.data.Data;
import rda.data.DataType;

/**
 *
 * @author kaeru
 */
public class FlatData  implements DataType{
    private final String name;
    private final Data data;

    private static Long count;
    
    private Long time, period;
    private Integer volume;
    
    public FlatData(Long time, Long period, int volume, int numberOfUser, int valueOfUser, int datamode, long seed) {
        this.name = "FlatType";
        this.data = new Data();
        
        this.time = time;
        this.period = period;
        this.volume = volume;
        
        //initialise
        data.init(numberOfUser, valueOfUser, datamode, seed);
        
        count = -1L;
    }
    
    @Override
    public MessageObject nextData(Long time) {
        count++;
        MessageObject msg = data.getData();
        
        if(count == volume.longValue()) msg = data.getPoison();
        if(count > volume) {
            msg = null;
            count = -1L;
        }
        
        return msg;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString(){
        Long n = time * 1000 / period;
        Long result = n * volume;
        
        return name + " DataN_" + result;
    }

    @Override
    public String toString(Long time) {
        return String.valueOf(volume);
    }
}
