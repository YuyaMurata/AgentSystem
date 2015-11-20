/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import com.ibm.agent.exa.AgentKey;
import java.util.ArrayList;
import java.util.List;
import rda.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class WindowGenerator extends TestParameter{
    public static List<MessageObject> dataSets = new ArrayList<>();
    
    public void init(){
        for(int i =0; i < NUMBER_OF_USER_AGENTS; i++){
            AgentKey agentKey = new AgentKey("useragent", new Object[]{"U#00"+i});
            Integer value = 1;
            
            MessageObject msg = new MessageObject(agentKey, value);
            
            dataSets.add(msg);
        }
    }
    
    public WindowGenerator() {
        init();
    }
    
    public static void main(String[] args) {
        new WindowGenerator();
        
        for(MessageObject msg : dataSets)
            System.out.println("Data:"+msg.toString());
    }
}
