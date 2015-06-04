package test;

import java.util.Random;

public class TestMain {
	private static final Random rand = new Random();
	public static void main(String[] args) {
		MessageQueue mqA = new MessageQueue("MQA");
		MessageQueue mqB = new MessageQueue("MQB");

		for(int i=0; i< 100; i++){
			if(rand.nextBoolean()) mqA.putMessage("Message No."+i);
			else mqB.putMessage("Message No."+i);
		}
		
		mqA.close();
		mqB.close();
	}
}
