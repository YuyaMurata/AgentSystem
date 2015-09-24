/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

/**
 *
 * @author kaeru
 */
public class AgentSystemLogger {
    //Logger SLF4J
    /**
    * AgentSyste_info.log element = Message Queue Length per sec
    * Consle Output Step Time per sec
    * vmstat.log CPU Avairability per sec
    */
    private static final Logger logger = LoggerFactory.getLogger(AgentSystemLogger.class);
    private static final AgentSystemLogger agentSystemLogger = new AgentSystemLogger();
    
    private AgentSystemLogger() {
    }
    
    public static AgentSystemLogger getInstance(){
        return agentSystemLogger;
    }
    
    public void print(Marker marker, String str, Object[] arr){
        //Console Out
        logger.info(marker, str, arr);
    }
    
    public void printAgentSystemSettings(Marker marker, String str, Object[] arr){
        printMQEvent(marker, str, arr);
        printMQLFile(marker, str, arr);
        printResults(marker, str, arr);
    }
    
    public void printMQEvent(Marker marker, String str, Object[] arr){
        //File Out
        logger.trace(marker, str, arr);
        
        //Console Out
        //print(marker, str, arr);
    }
    
    public void printMQLFile(Marker marker, String str, Object[] arr){
        //File Out
        logger.debug(marker, str, arr);
    }
    
    public void printResults(Marker marker, String str, Object[] arr){
        //File & System Out
        logger.warn(marker, str, arr);
    }
}
