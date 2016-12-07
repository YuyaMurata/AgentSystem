package test.other;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapNullTest {
    public static void main(String[] args) {
        Map map1 = new ConcurrentHashMap();
        Map map2 = new HashMap();
        
        map2.put("test", null);
        
        if(map1.get("test") == null){
            System.out.println("test.other.ConcurrentHashMapNullTest.main()");
        }else{
            
        }
    }
}
