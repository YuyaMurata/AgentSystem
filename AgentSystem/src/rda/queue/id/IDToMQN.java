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
        
        private List<AgentKey> keyList = new ArrayList<>();     
        public void setKey(AgentKey key){
            keyList.add(key);
        }
        
        private List<String> idList = new ArrayList<>();
        public void setID(String id){
            idList.add(id);
        }
        
        private List<String> mqNameList = new ArrayList<>();
        public void setMQName(String name){
            mqNameList.add(name);
        }
               
	public int toMQN(AgentKey key){
            return keyList.indexOf(key);
	}
        
        public int toMQN(String id){
            if(id.contains("RMQ")) return mqNameList.indexOf(id);
            else return idList.indexOf(id);
	}
        
        public String toID(String name){
            return idList.get(toMQN(name));
	}
        
        public String toID(AgentKey key){
            return idList.get(toMQN(key));
	}
        
        public AgentKey toKey(String id){
            return toKey(toMQN(id));
	}
        
        public AgentKey toKey(int sid){
            return keyList.get(sid);
        }
        
        /* hash (- -> +) confilict
        public int toMQN(AgentKey key){
            return Math.abs(key.hashCode()) % NUMBER_OF_QUEUE;
	}
        */
}
