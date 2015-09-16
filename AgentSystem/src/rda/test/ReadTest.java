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
    
    private static final Marker dataMarker = MarkerFactory.getMarker("data");
     private static final Marker fieldMarker = MarkerFactory.getMarker("field");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
	
	public static void main(String[] args) {
            ReadUser user = new ReadUser();
            //ReadLogUser log = new ReadLogUser();
		
            ArrayList<UserInfo> results = user.read(NUMBER_OF_USER_AGENTS);
            //log.get(NUMBER_USER);
            
            int i = 0;
            int total = 0;
            long countTotal = 0;
            StringBuilder sb = new StringBuilder();
            String[] name = new String[NUMBER_OF_USER_AGENTS+1];
            Integer[] data = new Integer[NUMBER_OF_USER_AGENTS+1];
            Long[] count = new Long[NUMBER_OF_USER_AGENTS+1];
            for(UserInfo result : results){
                sb.append(",{}");
                name[i] = result.getID();
                
                data[i] = result.getData();
                total = total + result.getData();
                
                count[i] = result.getCount();
                countTotal = countTotal + result.getCount();
                
                i++;
            }
            name[name.length-1] = "Total";
            data[data.length-1] = total;
            count[count.length-1] = countTotal;
            
            //Output
            logger.printResults(fieldMarker, "UserID"+sb.toString()+",{}", name);
            logger.printResults(dataMarker, "Transaction"+sb.toString()+",{}", data);
            logger.printResults(dataMarker, "ConnectionCount"+sb.toString()+",{}", count);
	}
}
