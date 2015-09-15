package rda.test;

import java.util.ArrayList;
import rda.agent.user.ReadLogUser;
import rda.agent.user.ReadUser;
import rda.agent.user.UserInfo;
import rda.property.SetProperty;

public class ReadTest implements SetProperty{
	
	public static void main(String[] args) {
            ReadUser user = new ReadUser();
            //ReadLogUser log = new ReadLogUser();
		
            ArrayList<UserInfo> results = user.read(NUMBER_OF_USER_AGENTS);
            //log.get(NUMBER_USER);
            
            for(UserInfo result : results)
                System.out.println(result.toString());
	}
}
