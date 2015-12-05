/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import com.ibm.agent.exa.AgentKey;

/**
 *
 * @author kaeru
 */
public class UnitTest {
    public static void main(String[] args) {
        System.out.println("AgentKey : "+new AgentKey("useragent", new Object[]{"U#001-1-2-3-4-5-6-7-8-9"}));
    }
}
