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
        public void setID(String agID){
            idList.add(agID);
        }
        
        private List<String> mqNameList = new ArrayList<>();
        public void setMQName(String name){
            mqNameList.add(name);
        }
               
	public int toSID(AgentKey key){
            return keyList.indexOf(key);
	}
        
        public String toMQN(AgentKey key){
            return mqNameList.get(toSID(key));
        }
        
        public int toSID(String id){
            if(id.contains("RMQ")) return mqNameList.indexOf(id);
            else if(id.contains("U#")) return Math.abs(id.hashCode()) %  mqNameList.size();
            else return idList.indexOf(id);
	}
        
        public String toAGID(String name){
            return idList.get(toSID(name));
	}
        
        public String toAGID(AgentKey key){
            return idList.get(IDToMQN.this.toSID(key));
	}
        
        public AgentKey toKey(String id){
            return toKey(toSID(id));
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
