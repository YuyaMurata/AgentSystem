package rda.queue.id;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import rda.data.profile.ProfileGenerator;

public class IDToMQN{
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
        
        public String reserveID(int rid){
            String agID = "R#"+dformat.format(serialID+rid);
            
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
                Integer range = agIDList.indexOf(agID) * 100 / numberOfQueue;
                ageMap.put(range.toString(), agID);
            }
        }
        
        //Setting ID, MQName List
        private List<String> agIDList = new ArrayList<>();
        public void setID(String agID){
            agIDList.add(agID);
        }
        
        //Only Age Aggregation
        private TreeMap ageMap = new TreeMap();
        private ProfileGenerator prof = ProfileGenerator.getInstance();
        public String ageToAGID(String uid){
            //Get Users Age
            String age = (String) prof.getProf(uid).get("Age");
            
            //Age -> agID
            String agID = (String) ageMap.floorEntry(age).getValue();
            
            //Get MessageQueue ID (Dist-Agent)
            return getDestAgent(agID, uid);
        }
        
        //Ditributed Map
        private HashMap<String, List<String>> distAgentMap = new HashMap<>();
        private String getDestAgent(String agID, String uid){
            //Get Dist AgentList
            List<String> distAGList = distAgentMap.get(agID);
            
            //System.out.println("AGList:: agID="+agID+" list="+distAGList);
            
            //Geet Dist-Agent
            Integer sid = agentHash(uid, distAGList.size());
            String destAGID = distAGList.get(sid);
            
            return destAGID;
        }
        
        //Hash Dist-Agent
        public Integer agentHash(String uid, int size){
            int hash = Math.abs(uid.hashCode());
            return  hash % size;
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
        
        public Integer getNumberOfActiveAgents(){
            return agIDList.size();
        }
        
        public List getAGIDList(){
            return agIDList;
        }
        
        public Boolean checkAGID(String agID){
            return agIDList.indexOf(agID) > -1;
        }
        
        // System Out
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(" --- Information ---\n");
            sb.append("List Size : "+agIDList.size());
            sb.append("\n --- AgentID List ---\n");
            sb.append(agIDList);
            
            return sb.toString();
        }

        public String getAGIDListToString(){
            StringBuilder sb = new StringBuilder();
            for(String id : agIDList)
                sb.append("," + id);
            
            return sb.toString();
        }
        
        private Integer numberOfQueue = 0;
        public void setNumQueue(Integer num){
            this.numberOfQueue = num;
        }
}