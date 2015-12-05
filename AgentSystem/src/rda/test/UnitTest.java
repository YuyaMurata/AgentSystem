/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import com.ibm.agent.exa.AgentKey;
import rda.test.unit.CreateAgentTest;

/**
 *
 * @author kaeru
 */
public class UnitTest {
    private void keyTest(){
        System.out.println("AgentKey : "+new AgentKey("useragent", new Object[]{"U#001-1-2-3-4-5-6-7-8-9"}));
    }
    
    private void createTest(){
        String userID = "U#000-1-2-3-4-567";
        new CreateAgentTest().createAgent(userID);
    }
    
    public static void main(String[] args) {
        UnitTest unit = new UnitTest();
        
        //unit.keyTest();
        
        unit.createTest();
    }
}
