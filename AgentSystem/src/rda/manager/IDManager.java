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
    private static IDManager manager = new IDManager();
    private IDManager(){}
    
    public static IDManager getInstance(){
        return manager;
    }
    
    public void initIDManager(){
        
    }
    
    private Map ageToAGIDMap = new TreeMap();
    private void initAgeToAGID(){
        for(int i=0; i < 10; i++){
            if(i >= agIDList.size()) break;
            ageToAGIDMap.put(i*10, agIDList.get(i));
        }
    }
    
    private Map agIDMap = new HashMap<>();
    private synchronized void setCandidateAgents(String pid, String cid){
        if(agIDMap.get(pid) == null) agIDMap.put(pid, new ArrayList<>());
        List candidateList = (List) agIDMap.get(pid);
        candidateList.add(cid);
        agIDMap.put(pid, candidateList);
    }
    
    private static Integer serialID = 0;
    public static synchronized String genID(){
        DecimalFormat dformat= new DecimalFormat("0000");
        return "R#"+dformat.format(serialID++);
    }
    
    private static List agIDList = new ArrayList();
    public static synchronized void regID(String agID){
        agIDList.add(agID);
    }
}
