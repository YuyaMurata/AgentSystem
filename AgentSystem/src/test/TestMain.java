package test;

import com.ibm.ws.xs.jdk5.java.util.TreeSet;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class TestMain extends Exception{
	private static final Random rand = new Random();
        private static final Integer NUM = 1000000;
        
        public TestMain(){
            super("TestMain");
        }
        
	public static void main(String[] args) {
		TestMain e = new TestMain();
                System.out.println(e.toString());
                
                ConcurrentSkipListMap<String, Integer> map1 = new ConcurrentSkipListMap<>();
                ConcurrentHashMap<String, Integer> map2 = new ConcurrentHashMap<>();
                
                Long start = System.currentTimeMillis();
                
                System.out.println("map1:");
                for(int i=0; i < NUM; i++){
                    Object[] arr = {"aa","bb","cc"};
                }
                
                /*
                for(int i=0; i < NUM; i++){
                    String key = "KEY:"+i%NUM;
                    map1.put(key, i);
                }
                System.out.println("Map1 Size_"+map1.size());
                //for(String key : map1.keySet())
                //    System.out.print("Key."+key+" Value."+map1.get(key)+", ");
                */
                Long stop = System.currentTimeMillis();
                System.out.println("\nMap1 Time : "+(stop-start));
                
                start = System.currentTimeMillis();
                System.out.println("\nmap2:");
                for(int i=0; i < NUM; i++){
                    String arr = new String("aa"+"bb"+"cc");
                }
                
                /*
                for(int i=0; i < NUM; i++){
                    String key = "KEY:"+i%NUM;
                    map2.put(key, i);
                }
                System.out.println("Map2 Size_"+map2.size());
                //for(String key : map2.keySet())
                //    System.out.print("Key."+key+" Value."+map2.get(key)+", ");
                */
                stop = System.currentTimeMillis();
                System.out.println("\nMap2 Time : "+(stop-start));
                
	}
}
