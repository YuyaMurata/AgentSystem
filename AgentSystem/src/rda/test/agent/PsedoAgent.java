/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.agent;

import rda.agent.template.AgentType;

/**
 *
 * @author kaeru
 */
public class PsedoAgent extends AgentType{
    private String id;
    public PsedoAgent(String id) {
        this.id = id;
    }
    
    @Override
    public void sendMessage(Object data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getID() {
        return id;
    }
    
}