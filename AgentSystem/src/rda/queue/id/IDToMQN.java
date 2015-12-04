package rda.queue.id;

import com.ibm.agent.exa.AgentKey;
import rda.property.SetProperty;

import java.util.ArrayList;
import java.util.List;

public class IDToMQN implements SetProperty{
	//AgentKey Define
	//private static final int HASH_MOD = 9973;
        private static IDToMQN idToMQN = new IDToMQN();
        
        public static IDToMQN getInstance(){
            return idToMQN;
        }
        
        private List<String> idList = new ArrayList<>();
        private List<AgentKey> keyList = new ArrayList<>();     
        public void setKey(String id, AgentKey key){
            idList.add(id);
            keyList.add(key);
        }
        
        public Object getKey(int sid){
            return keyList.get(sid);
        }
        
	public int toMQN(AgentKey key){
            return keyList.indexOf(key);
	}
        
        public int toMQN(String id){
            return idList.indexOf(id);
	}
        
        public String toID(AgentKey key){
            return idList.get(toMQN(key));
	}
        
        public Object toKey(String id){
            return keyList.get(toMQN(id));
	}
        
        /* hash (- -> +) confilict
        public int toMQN(AgentKey key){
            return Math.abs(key.hashCode()) % NUMBER_OF_QUEUE;
	}
        */
}
