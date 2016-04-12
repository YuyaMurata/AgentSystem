/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import rda.manager.LoggerManager;
import rda.manager.SystemManager;

/**
 * 
 * @author 悠也
 */
public class AgentSystemMain {
    private static SystemManager manager = SystemManager.getInstance();
    
    public static void main(String[] args) {
        prepare();
        logger();
        execute();
        shutdown();
        resultout();
    }
    
    private static void prepare(){
        manager.launchSystem();
    }
    
    private static void logger(){
        manager.logSchedule().start();
    }
    
    private static void execute(){
        manager.dataStream().start();
    }
    
    private static void shutdown(){
        manager.dataStream().stop();
        manager.logSchedule().stop();
        manager.shutdownSystem();
    }
    
    private static void resultout(){
        LoggerManager log = LoggerManager.getInstance();
        log.printAgentDBData();
    }
}