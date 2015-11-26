package rda.queue;

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
        
        private List<Object> idList = new ArrayList<>();     
        public void setID(Object obj){
            idList.add(obj);
        }
        
        public Object getID(int no){
            return idList.get(no);
        }
        
	public int toMQN(Object obj){
            return idList.indexOf(obj);
	}
        
        /* hash (- -> +) confilict
        public int toMQN(AgentKey key){
            return Math.abs(key.hashCode()) % NUMBER_OF_QUEUE;
	}
        */
}
