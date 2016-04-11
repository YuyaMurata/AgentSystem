/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import rda.db.DBAccess;
import rda.db.SQLReturnObject;

/**
 *
 * @author kaeru
 */
public class LoggerManager {
    private static final LoggerManager manager = new LoggerManager();
    private LoggerManager(){}
    
    public static LoggerManager getInstance(){
        return manager;
    }
    
    public void printAgentDBData(){
        DBAccess db = new DBAccess();
        SQLReturnObject obj = db.query("select * from useragent");
        obj.print();
    }
}
