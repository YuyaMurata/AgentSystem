/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data;

import com.ibm.agent.exa.AgentKey;
import java.util.ArrayList;
import java.util.Random;
import rda.property.SetProperty;
import rda.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class Data implements SetProperty{
    private static int count = -1;
    private static final Random rand = new Random();

    public Data() {}

    //Set All UserID(AgentKey)
    private ArrayList<AgentKey> agentKeyList = new ArrayList<>();
    public void init(){
        for(int i=0; i < NUMBER_OF_USER_AGENTS; i++){
            String userID = "U#00"+ i;
            agentKeyList.add(new AgentKey(AGENT_TYPE, new Object[]{userID}));
        }
    }
        
    private Integer keyNo(){
        count++;
        if(count == NUMBER_OF_USER_AGENTS) count = 0;
        return count;
    }
    
    private Integer keyRandomNo(){
        return rand.nextInt(NUMBER_OF_USER_AGENTS);
    }
    
    private Integer keyGaussRandomNo(){
        return (int)rand.nextGaussian()*NUMBER_OF_USER_AGENTS;
    }

    //Get Data userID = Call % NUMBER_USER_AGENTS
    public MessageObject getData(){
        return new MessageObject(agentKeyList.get(keyGaussRandomNo()), AGENT_DEFAULT_VALUE);
    }
    
    public MessageObject getPoison(){
        return new MessageObject(agentKeyList.get(keyNo()), -1);
    }
    
}
