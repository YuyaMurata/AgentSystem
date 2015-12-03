/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data;

import com.ibm.agent.exa.AgentKey;
import java.util.ArrayList;
import org.apache.commons.math3.random.RandomDataGenerator;
import rda.property.SetProperty;
import rda.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class Data implements SetProperty{
    private static int count = -1;
    private static final RandomDataGenerator rand = new RandomDataGenerator();
    private int mu, sigma;

    public Data() {}

    //Set All UserID(AgentKey)
    private ArrayList<AgentKey> agentKeyList = new ArrayList<>();
    public void init(){
        for(int i=0; i < NUMBER_OF_USER_AGENTS; i++){
            String userID = "U#00"+ i;
            agentKeyList.add(new AgentKey(AGENT_TYPE, new Object[]{userID}));
        }
        
        mu = NUMBER_OF_USER_AGENTS/2;
        sigma = (int) (2*NUMBER_OF_USER_AGENTS / 10);
    }
        
    private Integer keyNo(){
        count++;
        if(count == NUMBER_OF_USER_AGENTS) count = 0;
        return count; 
    }
    
    private Integer keyRandomNo(){
        return rand.nextInt(0, NUMBER_OF_USER_AGENTS-1);
    }
    
    private Integer keyGaussRandomNo(){
        int key = (int)(rand.nextGaussian(mu, sigma));
        if(key < 0) key = 0;
        else if(key >= NUMBER_OF_USER_AGENTS) key = NUMBER_OF_USER_AGENTS-1;
        
        return key;
    }

    //Get Data userID = Call % NUMBER_USER_AGENTS
    public MessageObject getData(){
        return new MessageObject(agentKeyList.get(keyNo()), AGENT_DEFAULT_VALUE);
    }
    
    public MessageObject getPoison(){
        return new MessageObject(agentKeyList.get(keyNo()), -1);
    }
    
}