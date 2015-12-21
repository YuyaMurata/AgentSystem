/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import rda.test.setter.TestSettings;
import rda.test.setter.TestParameter;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.math3.random.RandomDataGenerator;
import rda.data.profile.ProfileGenerator;
import rda.queue.obj.MessageObject;

/**
 *
 * @author kaeru
 */
public class DataTest extends TestParameter{
    private static TestSettings test = new TestSettings();
    private static RandomDataGenerator rand = new RandomDataGenerator();
    
    public static void main(String[] args) {
        test.setting();
        
        Long start,stop;
        
        TreeMap map = new TreeMap();
        ProfileGenerator gen = ProfileGenerator.getInstance();
        
        MessageObject msg;
        for(long t =0; t < TIME_RUN; t++){
            start = System.currentTimeMillis();
            while((msg = test.DATA_TYPE.generate(t)) != null){
                int key = Integer.valueOf((String)gen.getProf(msg.id).get("Age"));
                
                Long cnt = 0L;
                if(map.get(key) != null) cnt = (Long)map.get(key) + 1;
                map.put(key, cnt);
                
                //if(rand.nextInt(0, 10000000) == 7777777)
                //    TestSettings.decomposeTest(TestSettings.ID.sidToMQN(key));
            }
            
            stop = System.currentTimeMillis();
            System.out.println("Time = "+t+", ProcessTime = "+(stop-start)+" [ms]");
        }
        
        long total = 0L;
        for(Integer key : (Set<Integer>)map.keySet()){
            total = total+(Long)map.get(key);
            System.out.println(key+","+map.get(key));
        }
        
        System.out.println("Total:"+total);
        System.out.println(TestSettings.toTestSettingsString());
    }
}
