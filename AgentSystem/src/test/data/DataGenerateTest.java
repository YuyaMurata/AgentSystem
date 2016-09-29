/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.data;

import java.util.HashMap;
import java.util.Map;
import rda.data.test.TestData;
import rda.manager.TestCaseManager;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class DataGenerateTest implements SetProperty{
    public static void main(String[] args) {
        TestCaseManager tcmanager = TestCaseManager.getInstance();
        TestData data;
        tcmanager.initTestCase(preDataMap(), preProfMap());
        
        long cnt = 0L;
        long start = System.currentTimeMillis();
        for(long i=0; i < 10; i++)
            while(((data = tcmanager.datagen.generate(i)) != null)){
                cnt++;
            }
        long stop = System.currentTimeMillis();
        
        System.out.println("Data : "+cnt+" time="+(stop-start));
    }
    
    private static Map preProfMap(){
        Map map = new HashMap();
        map.put("AMOUNT_USERS", NUMBER_OF_USERS);
        map.put("MODE", DATA_MODE_PROFILE);
        map.put("RULE", NAME_RULE_USER);
        map.put("SEED", PROF_SEED);
        
        return map;
    }
    
    private static Map preDataMap(){
        Map map = new HashMap();
        map.put("TIME_RUN", TIME_RUN);
        map.put("TIME_PERIOD", TIME_PERIOD); 
        map.put("DATA_VOLUME", DATA_VOLUME); 
        map.put("AMOUNT_USER", NUMBER_OF_USERS); 
        map.put("AGENT_DEFAULT_VALUE", AGENT_DEFAULT_VALUE);
        map.put("SELECT_TYPE", DATA_SELECT_TYPE);
        map.put("MODE", DATA_MODE_GENERATE);
        map.put("SEED", DATA_SEED);
        
        return map;
    }
}
