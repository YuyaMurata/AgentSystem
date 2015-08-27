package rda.test;

import rda.agent.user.ReadLogUser;
import rda.agent.user.ReadUser;

public class ReadTest extends TestParameter{
	
	public static void main(String[] args) {
            ReadUser user = new ReadUser();
            ReadLogUser log = new ReadLogUser();
		
            user.read(NUMBER_USER);
            log.get(NUMBER_USER);
	}
}
