/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import rda.data.Data;
import rda.data.DataType;
import rda.data.test.TestData;

/**
 *
 * @author kaeru
 */
public class RouletteData implements DataType{
    private final String name;
    private final Data data;

    private static Long count;
    
    private Long term, period;
    private Long volume;
    
    public RouletteData(Long time, Long period, int volume, int numberOfUser, int valueOfUser, int datamode, long seed) {
        this.name = "RouletteType";
        this.data = new Data();
        
        this.term = time;
        this.period = period;
        this.volume = (time+1)*volume/2;
        
        //initialise
        data.init(numberOfUser, valueOfUser, datamode, seed);
        
        count = -1L;
    }
    
    @Override
    public TestData nextData(Long time) {
        if((time == 0) && (count == -1)) count = volume.longValue()-1; 
        
        count++;
        TestData test = null;
        if(rand.nextInt(4) > 0) test = (TestData) rouletteList.get(rand.nextInt(rouletteList.size()));
        else test = (TestData) data.getData();
        
        if(count > volume.longValue()) {
            test = null;
            count = -1L;
        }
        
        return test;
    }
    
    List rouletteList;
    Random rand = new Random();
    public void roulette(Integer n){
        rouletteList = new ArrayList();
        for(int i=0; i < n; i++)
            rouletteList.add(data.getData());
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString(){
        Long n = term * 1000 / period;
        Long result = n * volume;
        
        return name + " DataN_" + result;
    }

    @Override
    public String toString(Long time) {
        if(time == -1L) return toString();
        return String.valueOf(volume);
    }
}
