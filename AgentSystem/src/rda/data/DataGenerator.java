package rda.data;

import java.util.ArrayList;

import rda.property.SetProperty;
import rda.queue.MessageObject;

import com.ibm.agent.exa.AgentKey;

public class DataGenerator implements SetProperty{
	private static int count;
	private static final DataGenerator data = new DataGenerator();

        // Singleton
        private DataGenerator(){}

	public static DataGenerator getInstance(){
            return data;
	}

	//Set All UserID(AgentKey)
	private static final ArrayList<AgentKey> agentKey = new ArrayList<>();
	public void init(){
            count = 0;
            for(int i=0; i < NUMBER_OF_USER_AGENTS; i++){
                String userID = "U#00"+ i;
                agentKey.add(new AgentKey(AGENT_TYPE, new Object[]{userID}));
            }
	}

	//Get Data userID = Call % NM_USER_AGENTS
	private static final int value = AGENT_DEFAULT_VALUE;
	public MessageObject getData(){
		return new MessageObject(agentKey.get(count++%NUMBER_OF_USER_AGENTS), value);
	}

}
