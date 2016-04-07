/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import rda.manager.SystemManager;

/**
 *
 * @author 悠也
 */
public class AgentSystemMain {
    private static SystemManager manager = SystemManager.getInstance();
    
    public static void main(String[] args) {
        prepare();
        execute();
    }
    
    private static void prepare(){
        manager.launchSystem();
    }
    
    private static void logger(){
    }
    
    private static void execute(){
        manager.dataStream().start();
        manager.dataStream().stop();
    }
}
