package rda.queue;

import rda.property.SetProperty;

import com.ibm.agent.exa.AgentKey;

public class HashToMQN implements SetProperty{
	//AgentKey Define
	private static final int HASH_MOD = 9973;

	public static int toMQN(AgentKey agentKey){
            //return ((Math.abs(agentKey.hashCode()) % HASH_MOD) % SERVER_THREAD) % NUMBER_OF_QUEUE;
            return Math.abs(agentKey.hashCode()) % NUMBER_OF_QUEUE;
	}

}
