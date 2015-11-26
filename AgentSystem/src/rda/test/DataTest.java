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
import rda.queue.IDToMQN;
import rda.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class DataTest extends TestParameter implements SetDataType{
    public static void main(String[] args) {
        Long start,stop;
        
        IDToMQN idToMQN = IDToMQN.getInstance();
        
        HashMap<Integer, Long> map = new LinkedHashMap<>();
        
        for(int i=0; i < NUMBER_OF_USER_AGENTS; i++){
            idToMQN.setID(new AgentKey("useragent", new Object[]{"U#00"+i}));
            map.put(i, 0L);
            
            System.out.println("TestPrint_No."+i+"_ID="+idToMQN.getID(i));
            String str = idToMQN.getID(i).toString();
            System.out.println("ID_STRING=="+str);
        }
        
        MessageObject msg;
        for(long t =0; t < TIME_RUN; t++){
            start = System.currentTimeMillis();
            while((msg = DATA_TYPE.generate(t)) != null){
                int key = idToMQN.toMQN(msg.agentKey);
                Long cnt = map.get(key) + 1;
                map.put(key, cnt);
            }
            stop = System.currentTimeMillis();
            System.out.println("Data = "+t+", Time = "+(stop-start)+" [ms]");
        }
        
        for(Integer key : map.keySet())
            System.out.println(key+" : "+map.get(key));
    }
}
