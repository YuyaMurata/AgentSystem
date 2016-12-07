package test.data;

import java.util.HashMap;
import java.util.Map;
import rda.data.test.TestData;
import rda.data.type.RouletteData;
import rda.manager.TestCaseManager;
import rda.property.SetProperty;

public class DataGenerateTest implements SetProperty{
    public static void main(String[] args) {
        TestCaseManager tcmanager = TestCaseManager.getInstance();
        TestData data;
        tcmanager.initTestCase(preDataMap(), preProfMap());
        
        long cnt = 0L;
        Map dataLog = new HashMap();
        for(int i=0; i < 10; i++)
            dataLog.put("R#00"+i, 0);
        
        
        long start = System.currentTimeMillis();
        for(long i=0; i < 10; i++){
            ((RouletteData)tcmanager.datagen.getType()).roulette(10);
            for(Object key : dataLog.keySet()) dataLog.put(key, 0);
            
            while(((data = tcmanager.datagen.generate(i)) != null)){
                dataLog.put(data.toID, (int)dataLog.get(data.toID)+1);
                cnt++;
            }
            
            for(Object key : dataLog.keySet())
                System.out.println(key +","+ dataLog.get(key));
            System.out.println("");
        }
        long stop = System.currentTimeMillis();
        
        System.out.println("Data : "+cnt+" time="+(stop-start));
    }
    
    private static Map preProfMap(){
        Map map = new HashMap();
        map.put("AMOUNT_USERS", NUMBER_OF_USERS);
        map.put("MODE", 0);
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
        map.put("SELECT_TYPE", 3);
        map.put("MODE", DATA_MODE_GENERATE);
        map.put("SEED", DATA_SEED);
        
        return map;
    }
}
