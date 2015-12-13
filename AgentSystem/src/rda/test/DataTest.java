/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import java.util.Set;
import java.util.TreeMap;
import rda.agent.user.ProfileGenerator;
import rda.queue.obj.MessageObject;

/**
 *
 * @author kaeru
 */
public class DataTest extends TestParameter{
    private static TestSettings test = new TestSettings();
    public static void main(String[] args) {
        test.setting();
        
        Long start,stop;
        
        TreeMap map = new TreeMap();
        ProfileGenerator gen = ProfileGenerator.getInstance();
        
        MessageObject msg;
        for(long t =0; t < TIME_RUN; t++){
            start = System.currentTimeMillis();
            while((msg = test.DATA_TYPE.generate(t)) != null){
                //int key = Integer.valueOf((String)gen.getProf(msg.id).get("Age"));
                int key = test.ID.ageToSID((String)gen.getProf(msg.id).get("Age"));
                Long cnt = 0L;
                if(map.get(key) != null) cnt = (Long)map.get(key) + 1;
                map.put(key, cnt);
            }
            if(t % 60 == 0) TestSettings.decomposeTest();
            
            stop = System.currentTimeMillis();
            System.out.println("Time = "+t+", ProcessTime = "+(stop-start)+" [ms]");
        }
        
        long total = 0L;
        for(Integer key : (Set<Integer>)map.keySet()){
            total = total+(Long)map.get(key);
            System.out.println(key+","+map.get(key));
        }
        
        System.out.println("Total:"+total);
    }
}
