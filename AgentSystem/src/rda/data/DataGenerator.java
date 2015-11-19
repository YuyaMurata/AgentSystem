package rda.data;

import rda.property.SetProperty;
import rda.queue.MessageObject;

public class DataGenerator {
    private DataType type;
    
    public DataGenerator(DataType type) {
        this.type = type;
    }
    
    public MessageObject generate(Long time){
        return type.nextData(time);
    }
    
    public String getName(){
        return type.getName();
    }
    
    @Override
    public String toString(){
        return type.toString();
    }
    
    //Test
    public static void main(String[] args) {
        DataGenerator data = new DataGenerator(new ImpulseData());
        MessageObject msg;
        
        Long total = 0L;
        
        for(long t=0; t < SetProperty.TIME_RUN; t++)
            while((msg = data.generate(t)) != null){
                if(msg.data != -1) total = total + msg.data;
            }
        
        System.out.println("Total:"+data.toString() + ", Summary:" + total);
    }
}