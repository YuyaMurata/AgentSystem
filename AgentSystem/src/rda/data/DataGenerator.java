package rda.data;

import java.util.ArrayList;

import rda.property.SetProperty;
import rda.queue.MessageObject;

import com.ibm.agent.exa.AgentKey;

public class DataGenerator extends SetProperty{
	private static int count;
	private static final DataGenerator data = new DataGenerator();
	private DataGenerator(){
		count = 0;
	}

	// Singleton
	public static DataGenerator getInstance(){
		init();
		return data;
	}

	//Set All UserID(AgentKey)
	private static ArrayList<AgentKey> agentKey = new ArrayList<>();
	private static void init(){
		for(int i=0; i < NUMBER_OF_USER_AGENTS; i++){
			String userID = "U#00"+ i;
			agentKey.add(new AgentKey(AGENT_TYPE, new Object[]{userID}));
		}
	}

	//Get Data userID = Call % NM_USER_AGENTS
	private static final int value = 1;
	public MessageObject getData(){
		return new MessageObject(agentKey.get(count++%NUMBER_OF_USER_AGENTS), value);
	}

}
