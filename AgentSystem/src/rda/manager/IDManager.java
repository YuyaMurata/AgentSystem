/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author kaeru
 */
public class IDManager {
    private String rule;
    private DecimalFormat dformat;
    private TreeMap ageMap = new TreeMap();
    private Map regAgentMap = new HashMap();
    
    public IDManager(String rule){
        this.rule = rule;
        this.dformat= new DecimalFormat("0000");
    }
    
    private Integer serialID = 0;
    public synchronized String genID(){      
        String agID = rule+dformat.format(serialID++);
        return agID;
    }
    
    //Register InitAgent
    public synchronized void initRegID(String id){
        if(serialID-1 < 10){
            List agList = new ArrayList();
            agList.add(id);
            regAgentMap.put(id, agList);
            ageMap.put(serialID * 10, id);
        } else {
            List agList = (List)regAgentMap.get(ageMap.get(((serialID % 10)+1) * 10));
            agList.add(id);
        }
    }
    
    public synchronized void regID(String id){
        
    }
    
    public String ageToID(Integer age){
        return (String) ageMap.ceilingEntry(age).getValue();
    }
    
    public String getDestID(String originID){
        List destAgentList = (List) regAgentMap.get(originID);
        return (String)destAgentList.get(0);
    }
    
    //Test Print
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<IDManager>\n");
        
        sb.append("<<Age To AgentID>>\n");
        for(Object key : ageMap.keySet())
            sb.append(key+":"+ageMap.get(key)+"\n");
        
        sb.append("<<Register Key and Lists>>\n");
        for(Object key : regAgentMap.keySet())
            sb.append(key+":"+regAgentMap.get(key)+"\n");
        
        return sb.toString();
    }
}