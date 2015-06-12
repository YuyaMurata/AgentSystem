package rda.test;

import rda.agent.user.ReadLogUser;
import rda.agent.user.ReadUser;

public class ReadTest {
	private static final int NUM_OF_AGENTS = 1000;
	
	public static void main(String[] args) {
            ReadUser user = new ReadUser();
            ReadLogUser log = new ReadLogUser();
		
            user.read(NUM_OF_AGENTS);
            log.get(NUM_OF_AGENTS);
	}
}
