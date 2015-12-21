package rda.queue.id;

import java.text.DecimalFormat;
import rda.property.SetProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import rda.data.profile.ProfileGenerator;

public class IDToMQN implements SetProperty{
	//AgentKey Define
	//private static final int HASH_MOD = 9973;
        private static IDToMQN idToMQN = new IDToMQN();
        
        public static IDToMQN getInstance(){
            return idToMQN;
        }
        
        //Create AgentID
        private DecimalFormat dformat= new DecimalFormat("0000");
        private static Integer serialID = 0;
        public String createID(){
            //Create ID
            String agID = "R#"+dformat.format(serialID++);
            
            //AgentID sets IDList
            setID(agID);
            
            return agID;
        }
        
        private HashMap<String, String> familyMap = new HashMap<>();
        public void init(){
            //After Running Set Function
            for(String agID : agIDList){
                //Init Distirubuted Map <MQName, dist-list>
                distAgentMap.put(agID, new ArrayList<String>());
                distAgentMap.get(agID).add(agID);
                
                //Init Decomposed Map <AgentID, decomp-count>
                familyMap.put(agID, agID);
                
                //Init AgeToMQN Map <Age, SID(MQ No.)>
                Integer range = agIDList.indexOf(agID) * 100 / NUMBER_OF_QUEUE;
                ageMap.put(range.toString(), agID);
            }
        }
        
        //Setting ID, MQName List
        private List<String> agIDList = new ArrayList<>();
        private void setID(String agID){
            agIDList.add(agID);
            //setMQName(agID);
        }
        
        //private List<String> mqNameList = new ArrayList<>();
        //private void setMQName(String agID){
        //    mqNameList.add("RMQ"+toSID(agID));
        //}
        
        //Translation SID, UID(*), AGE(*), MQN, AGID <-> SID, MQN, AGID
        //public Integer toSID(String id){
        //    if(id.contains("RMQ")) return mqNameList.indexOf(id);
        //    else if(id.contains("U#")){
        //        int sid = Math.abs(id.hashCode()) % NUMBER_OF_QUEUE;  
        //        return getDestinationMQ(sidToMQN(sid));
        //    }
        //    else return agIDList.indexOf(id);
	//}
        
        //public String agIDToMQN(String agID){
        //    return mqNameList.get(toSID(agID));
        //}
        
        //public String mqnToAGID(String name){
        //    return agIDList.get(toSID(name));
	//}
        
        //public String sidToAGID(int sid){
        //    return agIDList.get(sid);
        //}
        
        //public String sidToMQN(int sid){
        //    return mqNameList.get(sid);
        //}
        
        //Only Age Aggregation
        private TreeMap ageMap = new TreeMap();
        private ProfileGenerator prof = ProfileGenerator.getInstance();
        public String ageToAGID(String uid){
            //Get Users Age
            String age = (String) prof.getProf(uid).get("Age");
            
            //Age -> agID
            String agID = (String) ageMap.floorEntry(age).getValue();
            
            //Get MessageQueue ID (Dist-Agent)
            return getDestAgent(agID);
        }
        
        //Ditributed Map
        private HashMap<String, List<String>> distAgentMap = new HashMap<>();
        private String getDestAgent(String agID){
            //Get Dist AgentList
            List<String> distAGList = distAgentMap.get(agID);
            
            //Roulette get Dist-Agent
            Integer sid = agentRoulette(agID, distAGList.size());
            
            return distAGList.get(sid);
        }
        
        //Roulette Dist-Agent
        private HashMap<String, Integer> robin = new HashMap();
        public Integer agentRoulette(String agID, int size){
            int cnt = 0;
            
            if(robin.get(agID) != null) cnt = robin.get(agID) + 1;
            if(cnt > size-1) cnt = 0;
            
            robin.put(agID, cnt);
            
            return cnt;
        }
        
        //Add Distributed Agent
        public void addDistAgent(String pid, String cid){
            //Search Root ID
            String agID = searchParent(pid);
            
            //Distributed List R#00 -> Dist R#M~R#N
            distAgentMap.get(agID).add(cid);
            
            //Relation Dist-AGID AGID
            familyMap.put(cid, pid);
        }
        
        //Search Root AgentID
        private String searchParent(String pid){
            if(pid == familyMap.get(pid)){
                return pid;
            }else{
                return searchParent(familyMap.get(pid));
            }
        }
        
        // System Out
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(" --- Information ---\n");
        //    sb.append("List Size : "+mqNameList.size()+", "+agIDList.size());
        //    sb.append("\n --- MessageQueue List ---\n");
        //    sb.append(mqNameList);
            sb.append("\n --- AgentID List ---\n");
            sb.append(agIDList);
            
            return sb.toString();
        }
        
        //public String getMQNameList(){
        //    StringBuilder sb = new StringBuilder();
        //    for(String name : mqNameList)
        //        sb.append("," + name);
        //    
        //    return sb.toString();
        //}
        
        public String getAGIDList(){
            StringBuilder sb = new StringBuilder("AgentID");
            for(String id : agIDList)
                sb.append("," + id);
            
            return sb.toString();
        }
        
        //public void outputDistID(){
        //    for(Object key : decompMap.keySet())
        //        System.out.println("DECOMPOSE::"+key+"_"+decompMap.get((String)key));
        //    
        //    for(Object key : distMQNMap.keySet())
        //        System.out.println("DISTRIBUTE::"+distMQNMap.get(key));
        //}
        
        /* hash (- -> +) confilict
        public int toMQN(AgentKey key){
            return Math.abs(key.hashCode()) % NUMBER_OF_QUEUE;
	}
        */
}
