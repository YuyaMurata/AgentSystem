/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import rda.agent.template.AgentLogPrinterTemplate;
import rda.db.DBAccess;
import rda.db.SQLReturnObject;
import rda.log.AgentLogPrint;
import rda.manager.AgentMessageQueueManager;

/**
 *
 * @author kaeru
 */
public class AggregateAgentLogPrinter extends AgentLogPrinterTemplate{
    private static AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
    private DBAccess db;
    private String agenttype;
    
    public AggregateAgentLogPrinter(String agenttype){
        this.db = new DBAccess();
        this.agenttype = agenttype;
    }
    
    //RankAgent MessageQueue Status
     private void printAgentObserver(){
        StringBuilder place = new StringBuilder("MessageQueue");
        List field = new ArrayList();
        List data = new ArrayList();
        Map map = new HashMap();
        
        for(int i=0; i < manager.getObserver().size(); i++){
            place.append(",{}");
            field.add(manager.getObserver().get(i).getName());
            data.add(manager.getObserver().get(i).notifyState().longValue());
        }
        
        map.put("Place", place.toString());
        map.put("Field", field);
        map.put("Data", data);
        
        //MessageQueue Observer QueueLength
        System.out.println("> QueueLength:\n"+mapToString(map));
         AgentLogPrint.printMessageQueueLog(map);
        
        //Agent Inner QueueLength
        //SQLReturnObject obj = db.query();
        //Map map = obj.toMap("Length", 2);
        //System.out.println("> QueueLength:\n"+obj.toString(map));
    }
    
    //RankAgent Database Transaction
    private void printAgentTransaction(){
        SQLReturnObject obj = db.query();
        
        Map map = obj.toMap("Transaction", 0);
        System.out.println("> DataTransaction:\n"+mapToString(map));
        AgentLogPrint.printAgentTransaction(map);
    }
    
    //RankAgent Database MessageLatency
    private void printAgentLatency(){
        SQLReturnObject obj = db.query();
        
        Map map = obj.toMap("Latency", 1);
        System.out.println("> MessageLatency:\n"+mapToString(map));
        AgentLogPrint.printMessageLatency(map);
    }
    
    //String Print Out
    private String mapToString(Map map){
        StringBuilder sb = new StringBuilder();
        //sb.append((String)map.get("Place"));
        //sb.append("\n");
        
        String s1 = ((List<String>)map.get("Field")).stream()
                        .collect(Collectors.joining(","));
        sb.append(s1);
        sb.append("\n");
        
        String s2 = ((List<Long>)map.get("Data")).stream()
                        .map( n -> n.toString())
                        .collect(Collectors.joining(","));
        
        sb.append(s2);
        
        return sb.toString();
    }
    
    @Override
    public void printer() {
        System.out.println(agenttype+" - Log Printer : ");
        printAgentTransaction();
        printAgentLatency();
        printAgentObserver();
    }
    
}
