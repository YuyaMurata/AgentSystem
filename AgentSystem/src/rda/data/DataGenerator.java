package rda.data;

import java.util.ArrayList;

import rda.property.SetProperty;
import rda.queue.MessageObject;

import com.ibm.agent.exa.AgentKey;

public class DataGenerator implements SetProperty{
    private static int count = -1;
    private static final DataGenerator data = new DataGenerator();

    // Singleton
    private DataGenerator(){}

    public static DataGenerator getInstance(){
        return data;
    }

    //Set All UserID(AgentKey)
    private static final ArrayList<AgentKey> agentKeyList = new ArrayList<>();
    public void init(){
        for(int i=0; i < NUMBER_OF_USER_AGENTS; i++){
            String userID = "U#00"+ i;
            agentKeyList.add(new AgentKey(AGENT_TYPE, new Object[]{userID}));
        }
    }
        
    private Integer keyNo(){
        count++;
        if(count == NUMBER_OF_USER_AGENTS) count = 0;
        return count;
    }

    //Get Data userID = Call % NUMBER_USER_AGENTS
    public MessageObject getData(){
        return new MessageObject(agentKeyList.get(keyNo()), AGENT_DEFAULT_VALUE);
    }
}