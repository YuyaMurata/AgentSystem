package rda.queue.id;

import com.ibm.agent.exa.AgentKey;
import rda.property.SetProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import rda.agent.user.ProfileGenerator;

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
               
	public int keyToSID(AgentKey key){
            return keyList.indexOf(key);
	}
        
        public String keyToMQN(AgentKey key){
            return mqNameList.get(keyToSID(key));
        }
        
        public String agIDToMQN(String agID){
            return mqNameList.get(toSID(agID));
        }
        
        private int round = -1;
        public Integer toSID(String id){
            if(id.contains("RMQ")) return mqNameList.indexOf(id);
            else if(id.contains("U#")){
                int sid = Math.abs(id.hashCode()) % NUMBER_OF_QUEUE;
                int m = decompositionMap.get(sidToMQN(sid)).size();
                if(round >= m) round = 0;
                
                
                System.out.println("TOSID___test::"+decompositionMap);
                System.out.println("TOSID___test::"+decompositionMap.get(sidToMQN(sid)));
                    
                return toSID(decompositionMap.get(sidToMQN(sid)).get(round++));
            }
            else return idList.indexOf(id);
	}
        
        public String mqnToAGID(String name){
            return idList.get(toSID(name));
	}
        
        public String keyToAGID(AgentKey key){
            return idList.get(IDToMQN.this.keyToSID(key));
	}
        
        public AgentKey agIDToKey(String id){
            return sidToKey(toSID(id));
	}
        
        public AgentKey sidToKey(int sid){
            return keyList.get(sid);
        }
        
        public String sidToMQN(int sid){
            return mqNameList.get(sid);
        }
        
        private TreeMap ageMap = new TreeMap();
        public void setAgeToTreeMap(){
            for(int i=0; i < mqNameList.size(); i++){
                Integer range = (int)i * 100 / NUMBER_OF_QUEUE;
                ageMap.put(range.toString(), i);
            }
        }
        
        private int round2 = -1;
        private ProfileGenerator prof = ProfileGenerator.getInstance();
        public Integer ageToSID(String uid){
            String age = (String) prof.getProf(uid).get("Age");
            int sid = (Integer) ageMap.lowerEntry(age).getValue();
            int m = decompositionMap.get(sidToMQN(sid)).size();
            if(round2 >= m) round2 = 0;
            round2++;
            System.out.println("test::"+decompositionMap);
            System.out.println("test::"+decompositionMap.get(sidToMQN(sid)));
            
            return toSID(decompositionMap.get(sidToMQN(sid)).get(round2));
        }  
        
        private HashMap<Object, List<String>> decompositionMap = new HashMap<>();
        public String setDecomposeMap(String name){
            if(decompositionMap.get(name) == null){
                List<String> decomposeList = new ArrayList<>();
                decomposeList.add(name);
                decompositionMap.put(name, decomposeList);
                
                return null;
            } else{
                String agID = mqnToAGID(name)+"-"+(decompositionMap.get(name).size()-1);
                return agID;
            }
        }
        
        public void addDecomposeList(String name, String agID){
            decompositionMap.get(name).add(agIDToMQN(agID));
        }
        
        // System Out
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
