package rda.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.reader.ReadAgent;
import rda.data.fileout.OutputData;
import rda.manager.AgentManager;
import rda.manager.IDManager;

public class AgentQuerySchedule extends Thread{
    IDManager id;
    ReadAgent agent;
    public AgentQuerySchedule(AgentManager manager) {
        IDManager id = manager.getIDManager();
        ReadAgent agent = new ReadAgent();
        OutputData out = new OutputData("AppCountQuery.txt");
    }
    
    public Boolean runnable = true;
    
    public void run(){
        while(runnable){
            Long start = System.currentTimeMillis();
            
            Map map = id.getIDandDestLists();
            Map<Object, Long> aggMap = new HashMap();
            for(Object agid : map.keySet()){
                Long count = 0L;
                for(String destID : (List<String>)map.get(agid)){
                    count = count + agent.read(destID).getData();
                }
                aggMap.put(agid, count);
            }
            
            StringBuilder sb = new StringBuilder();
            for(Object agID : aggMap.keySet()){
                sb.append(agID);
            }
            
            for(Object agID : aggMap.keySet()){
                sb.append(aggMap.get(agID));
            }
            
            Long sleep = System.currentTimeMillis() - start;
            
            try {
                if(sleep < 1000)
                    Thread.sleep(1000-sleep);
            } catch (InterruptedException ex) {
            }
        }
    }
}