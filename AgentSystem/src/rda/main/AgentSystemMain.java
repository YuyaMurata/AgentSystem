/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import rda.manager.AgentMessageQueueManager;
import rda.manager.SystemManager;
import rda.query.AgentQuerySchedule;

/**
 * 
 * @author 悠也
 */
public class AgentSystemMain {
    private static SystemManager manager = SystemManager.getInstance();
    
    public static void main(String[] args) {
        prepare();
        execute();
        query();
        logger();
        shutdown();
    }
    
    private static void prepare(){
        manager.launchSystem();
    }
    
    private static void logger(){
        manager.startLogger();
    }
    
    private static void execute(){
        manager.dataStream().start();
    }
    
    private static void shutdown(){
        manager.dataStream().stop();
        manager.stopLogger();
        agent.runnable = false;
        manager.shutdownSystem();
    }
    
    private static AgentQuerySchedule agent;
    private static void query(){
        agent = new AgentQuerySchedule(AgentMessageQueueManager.getInstance());
        agent.start();
    }
}