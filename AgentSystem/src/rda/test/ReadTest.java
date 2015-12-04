package rda.test;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.user.UserInfo;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;
import rda.result.ReadALLAgents;

public class ReadTest implements SetProperty{
    
    private static final Marker dataMarker = MarkerFactory.getMarker("data");
    private static final Marker fieldMarker = MarkerFactory.getMarker("field");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
	
	public static void main(String[] args) {
            ReadALLAgents user = new ReadALLAgents();
            //ReadLogUser log = new ReadLogUser();
		
            ArrayList<UserInfo> results = user.read();
            //log.get(NUMBER_USER);

            long total = 0;
            long countTotal = 0;
            StringBuilder sb = new StringBuilder();
            List<String> name = new ArrayList<>();
            List<Long> data = new ArrayList<>();
            List<Long> count = new ArrayList<>();
            for(UserInfo result : results){
                sb.append(",{}");
                name.add(result.getID());
                
                data.add(result.getData());
                total = total + result.getData();
                
                count.add(result.getCount());
                countTotal = countTotal + result.getCount();
            }
            name.add("Total");
            data.add(total);
            count.add(countTotal);
            
            //Output
            logger.printResults(fieldMarker, "UserID"+sb.toString()+",{}", name.toArray(new String[name.size()]));
            logger.printResults(dataMarker, "Transaction"+sb.toString()+",{}", data.toArray(new Long[data.size()]));
            logger.printResults(dataMarker, "ConnectionCount"+sb.toString()+",{}", count.toArray(new Long[count.size()]));
	}
}
