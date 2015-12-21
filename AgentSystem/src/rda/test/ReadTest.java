package rda.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import rda.agent.client.AgentConnection;
import rda.agent.user.reader.UserInfo;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;
import rda.agent.reader.ReadALLAgents;

public class ReadTest implements SetProperty{

    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
	
	public static void main(String[] args) {
            AgentConnection ag = AgentConnection.getInstance();
            
            ReadALLAgents user = new ReadALLAgents();
            //ReadLogUser log = new ReadLogUser();
		
            Collection<UserInfo> results = user.read();
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
            
            ag.close();
            
            //Output
            logger.printResults(logger.fieldMarker, "ID"+sb.toString(), name.toArray(new String[name.size()]));
            logger.printResults(logger.dataMarker, "Transaction"+sb.toString(), data.toArray(new Long[data.size()]));
            logger.printResults(logger.dataMarker, "Connection"+sb.toString(), count.toArray(new Long[count.size()]));
            logger.printResults(logger.dataMarker, "Total,{},{}", new Object[]{total, countTotal});
	}
}
