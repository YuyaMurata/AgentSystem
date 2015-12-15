package test;

import java.util.Random;
import org.apache.commons.math3.random.RandomDataGenerator;

public class TestMain extends Exception{
	private static final RandomDataGenerator rand1 = new RandomDataGenerator();
        private static final Random rand2 = new Random();
        private static final Integer NUM = 1000000;
	public static void main(String[] args) {
            long start,stop;
            
            start = System.currentTimeMillis();
            for(int i=0; i < 10000; i++)
                rand1.nextInt(0, 100);
            stop =  System.currentTimeMillis();
            System.out.println("RandomDataGenerator(0,100):"+(stop-start)+"[ms]");
            
            start = System.currentTimeMillis();
            for(int i=0; i < 10000; i++){
                Integer age = (int) rand1.nextGaussian(50, 10);
                if((age > 100) || (age < 0)) age = rand1.nextInt(0, 100);
            }
            stop =  System.currentTimeMillis();
            System.out.println("RandomDataGenerator(0,100):"+(stop-start)+"[ms]");
            
            start = System.currentTimeMillis();
            for(int i=0; i < 10000; i++)
                rand1.nextInt(0, 0);
            stop =  System.currentTimeMillis();
            System.out.println("RandomDataGenerator(0,0):"+(stop-start)+"[ms]");
            
            start = System.currentTimeMillis();
            for(int i=0; i < 10000; i++)
                rand2.nextInt(100);
            stop =  System.currentTimeMillis();
            System.out.println("Random(100):"+(stop-start)+"[ms]");
	}
}
