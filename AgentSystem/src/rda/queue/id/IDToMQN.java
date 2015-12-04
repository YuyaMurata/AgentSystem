package rda.queue.id;

import rda.property.SetProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IDToMQN implements SetProperty{
	//AgentKey Define
	//private static final int HASH_MOD = 9973;
        private static IDToMQN idToMQN = new IDToMQN();
        
        public static IDToMQN getInstance(){
            return idToMQN;
        }
        
        private HashMap<Object, String> keyToidMap = new HashMap<>();
        private List<Object> keyList = new ArrayList<>();     
        public void setKey(String id, Object key){
            keyToidMap.put(key, id);
            keyList.add(key);
        }
        
        public Object getKey(int sid){
            return keyList.get(sid);
        }
        
	public int toMQN(Object key){
            return keyList.indexOf(key);
	}
        
        public String toID(Object key){
            return keyToidMap.get(key);
	}
        
        /* hash (- -> +) confilict
        public int toMQN(AgentKey key){
            return Math.abs(key.hashCode()) % NUMBER_OF_QUEUE;
	}
        */
}
