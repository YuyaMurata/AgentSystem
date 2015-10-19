package test;

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
                
                Integer a = Integer.MAX_VALUE;
                System.out.println("a="+a);
                a = a+1;
                System.out.println("a="+a);
                a=a+10;
                System.out.println("a="+a);
                
	}
}
