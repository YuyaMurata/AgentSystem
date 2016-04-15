/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rda.db.DBAccess;
import rda.db.SQLReturnObject;
import rda.log.LogSchedule;

/**
 *
 * @author kaeru
 */
public class LoggerManager {
    private static final LoggerManager manager = new LoggerManager();
    private DBAccess db;
    private LogSchedule log;
    private LoggerManager(){}
    
    public static LoggerManager getInstance(){
        return manager;
    }
    
    public void initLoggerManager(Map loggerMap){
        this.db = new DBAccess();
        this.log = new LogSchedule(loggerMap);
    }
    
    public LogSchedule getLogSchedule(){
        return log;
    }
    
    private Map latencyMap = new ConcurrentHashMap();
    public void putMSGLatency(String agID, Long latency){
        latencyMap.put(agID, latency);
    }
    
    public void printAgentDBData(){
        SQLReturnObject obj = db.query("select * from useragent");
        obj.print();
    }
}
