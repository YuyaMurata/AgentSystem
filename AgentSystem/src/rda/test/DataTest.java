/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import com.ibm.agent.exa.AgentKey;
import java.util.HashMap;
import java.util.LinkedHashMap;
import rda.data.SetDataType;
import static rda.data.SetDataType.DATA_TYPE;
import rda.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class DataTest extends TestParameter implements SetDataType{
    public static void main(String[] args) {
        HashMap<AgentKey, Long> map = new LinkedHashMap<>();
        
        MessageObject msg;
        for(long t =0; t < TIME_RUN; t++)
            while((msg = DATA_TYPE.generate(t)) != null){
                if(map.get(msg.agentKey) == null) map.put(msg.agentKey, 1L);
                else {
                    Long cnt = map.get(msg.agentKey) + 1;
                    map.put(msg.agentKey, cnt);
                }
            }
        
        for(AgentKey key : map.keySet())
            System.out.println(map.get(key));
    }
}
