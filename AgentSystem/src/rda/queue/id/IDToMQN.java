package rda.queue.id;

import com.ibm.agent.exa.AgentKey;
import rda.property.SetProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
        
        public Integer toSID(String id){
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
        
        private TreeMap ageMap = new TreeMap();
        public void setAgeToTreeMap(){
            for(int i=0; i < mqNameList.size(); i++){
                Integer range = (int)i * 100 / mqNameList.size();
                ageMap.put(range.toString(), i);
            }
        }
        
        public Integer ageToSID(String age){
            return (Integer) ageMap.floorEntry(age).getValue();
        }  
        
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(" --- Information ---\n");
            sb.append("List Size : "+mqNameList.size()+", "+idList.size()+", "+keyList.size());
            sb.append("\n --- MessageQueue List ---\n");
            sb.append(mqNameList);
            sb.append("\n --- AgentID List ---\n");
            sb.append(idList);
            sb.append("\n --- AgentKey List ---\n");
            sb.append(keyList);
            
            return sb.toString();
        }
        
        public String getMQNameList(){
            StringBuilder sb = new StringBuilder();
            for(String name : mqNameList)
                sb.append("," + name);
            
            return sb.toString();
        }
        
        public String getAGIDList(){
            StringBuilder sb = new StringBuilder();
            for(String id : idList)
                sb.append("," + id);
            
            return sb.toString();
        }
        
        /* hash (- -> +) confilict
        public int toMQN(AgentKey key){
            return Math.abs(key.hashCode()) % NUMBER_OF_QUEUE;
	}
        */
}
