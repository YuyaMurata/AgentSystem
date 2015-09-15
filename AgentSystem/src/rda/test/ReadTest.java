package rda.test;

import java.util.ArrayList;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.user.ReadLogUser;
import rda.agent.user.ReadUser;
import rda.agent.user.UserInfo;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;

public class ReadTest implements SetProperty{
    
    private static final Marker readMarker = MarkerFactory.getMarker("Read AgentInfo");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
	
	public static void main(String[] args) {
            ReadUser user = new ReadUser();
            //ReadLogUser log = new ReadLogUser();
		
            ArrayList<UserInfo> results = user.read(NUMBER_OF_USER_AGENTS);
            //log.get(NUMBER_USER);
            
            int i = 0;
            int total = 0;
            StringBuilder sb = new StringBuilder();
            String[] name = new String[NUMBER_OF_USER_AGENTS+1];
            Integer[] data = new Integer[NUMBER_OF_USER_AGENTS+1];
            Long[] count = new Long[NUMBER_OF_USER_AGENTS];
            for(UserInfo result : results){
                sb.append(",{}");
                name[i] = result.getID();
                data[i] = result.getData();
                total = total + result.getData();
                count[i] = result.getCount();
                i++;
            }
            name[name.length-1] = "Total";
            data[data.length-1] = total;
            
            //Output
            logger.printResults(readMarker, "UserID"+sb.toString()+",{}", name);
            logger.printResults(readMarker, "Data"+sb.toString()+",{}", data);
            logger.printResults(readMarker, "ConnectionCount"+sb.toString(), count);
	}
}
