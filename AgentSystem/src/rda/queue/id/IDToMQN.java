package rda.queue.id;

import rda.property.SetProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import org.apache.commons.math3.random.RandomDataGenerator;
import rda.agent.user.ProfileGenerator;

public class IDToMQN implements SetProperty{
	//AgentKey Define
	//private static final int HASH_MOD = 9973;
        private RandomDataGenerator rand = new RandomDataGenerator();
        private static IDToMQN idToMQN = new IDToMQN();
        
        public static IDToMQN getInstance(){
            return idToMQN;
        }
        
        public void init(){
            //After Running Set Function
            for(int i=0; i < mqNameList.size(); i++){
                //Init Distirubuted Map <MQName, dist-list>
                distMQNMap.put(sidToMQN(i), new ArrayList<String>());
                distMQNMap.get(sidToMQN(i)).add(sidToMQN(i));
                
                //Init Decomposed Map <AgentID, decomp-count>
                decompMap.put(sidToAGID(i), 0);
                
                //Init AgeToMQN Map <Age, SID(MQ No.)>
                Integer range = (int)i * 100 / NUMBER_OF_QUEUE;
                ageMap.put(range.toString(), i);
            }
        }
        
        //Setting ID, MQName List
        private List<String> agIDList = new ArrayList<>();
        public void setID(String agID){
            agIDList.add(agID);
            setMQName(agID);
        }
        
        private List<String> mqNameList = new ArrayList<>();
        private void setMQName(String agID){
            mqNameList.add("RMQ"+toSID(agID));
        }
        
        //Translation SID, UID(*), AGE(*), MQN, AGID <-> SID, MQN, AGID
        public Integer toSID(String id){
            if(id.contains("RMQ")) return mqNameList.indexOf(id);
            else if(id.contains("U#")){
                int sid = Math.abs(id.hashCode()) % NUMBER_OF_QUEUE;  
                return getDestinationMQ(sidToMQN(sid));
            }
            else return agIDList.indexOf(id);
	}
        
        public String agIDToMQN(String agID){
            return mqNameList.get(toSID(agID));
        }
        
        public String mqnToAGID(String name){
            return agIDList.get(toSID(name));
	}
        
        public String sidToAGID(int sid){
            return agIDList.get(sid);
        }
        
        public String sidToMQN(int sid){
            return mqNameList.get(sid);
        }
        
        //Only Age Aggregation
        private TreeMap ageMap = new TreeMap();
        private ProfileGenerator prof = ProfileGenerator.getInstance();
        public Integer ageToSID(String uid){
            String age = (String) prof.getProf(uid).get("Age");
            int sid = (Integer) ageMap.lowerEntry(age).getValue();
            return getDestinationMQ(sidToMQN(sid));
        }
        
        //Ditributed Map
        private HashMap<String, List<String>> distMQNMap = new HashMap<>();
        private Integer getDestinationMQ(String mqn){
            List<String> mqList = distMQNMap.get(mqn);
            Integer mq = toSID(mqList.get(rand.nextInt(0, mqList.size()-1)));
            return mq;
        }
        
        //Add List Distributed Agent
        public void addDistributedAgent(String name, String agID){
            String origin = mqnToAGID(name).split("-")[0];
            distMQNMap.get(agIDToMQN(origin)).add(agIDToMQN(agID));
        }
        
        //Decompose Map
        private HashMap<Object, Integer> decompMap = new HashMap<>();
        public String getDecomposeID(String name){
            //AgID (Parent)
            String agID = mqnToAGID(name);
            
            //Create Distiributed AgID (Child)
            StringBuilder distAGID = new StringBuilder(agID);
            distAGID.append("-");
            distAGID.append(decompMap.get(agID));
            
            //Parent AgID dist count +1
            decompMap.put(agID, decompMap.get(agID)+1);
            //Child AgID register
            decompMap.put(distAGID.toString(), 0);
            
            return distAGID.toString();
        }
        
        // System Out
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(" --- Information ---\n");
            sb.append("List Size : "+mqNameList.size()+", "+agIDList.size());
            sb.append("\n --- MessageQueue List ---\n");
            sb.append(mqNameList);
            sb.append("\n --- AgentID List ---\n");
            sb.append(agIDList);
            
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
            for(String id : agIDList)
                sb.append("," + id);
            
            return sb.toString();
        }
        
        public void outputDistID(){
            for(Object key : decompMap.keySet())
                System.out.println("DECOMPOSE::"+key+"_"+decompMap.get((String)key));
            
            for(Object key : distMQNMap.keySet())
                System.out.println("DISTRIBUTE::"+distMQNMap.get(key));
        }
        
        /* hash (- -> +) confilict
        public int toMQN(AgentKey key){
            return Math.abs(key.hashCode()) % NUMBER_OF_QUEUE;
	}
        */
}
