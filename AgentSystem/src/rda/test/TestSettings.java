/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import com.ibm.agent.exa.AgentKey;
import rda.data.DataGenerator;
import rda.data.MountData;
import rda.queue.id.IDToMQN;

/**
 *
 * @author kaeru
 */
public class TestSettings extends TestParameter {
    public DataGenerator DATA_TYPE;
    private void dataset(){
        MountData type = new MountData(
                TIME_RUN, 
                TIME_PERIOD, 
                VOLUME, 
                NUMBER_OF_USER, 
                VALUE,
                DATA_MODE
        );
        
        DATA_TYPE = new DataGenerator(type);
    }
    
    public static IDToMQN ID = IDToMQN.getInstance();
    private void idset(){
        for(int i=0; i < NUMBER_OF_AGENTS; i++){
            ID.setID("R#00"+i);
            ID.setKey(new AgentKey(AGENT_TYPE, new Object[]{"R#00"+i}));
            ID.setMQName("RMQ#"+i);
        }
        
        ID.setAgeToTreeMap();
    }
    
    private static int decompose = NUMBER_OF_AGENTS-1;
    public static void decomposeTest(){
        System.out.println("Decompose Agents!!");
        decompose++;
        
        ID.setID("R#00"+decompose);
        ID.setKey(new AgentKey(AGENT_TYPE, new Object[]{"R#00"+decompose}));
        ID.setMQName("RMQ#"+decompose);
        
        ID.setAgeToTreeMap();
    }
    
    public void setting(){
        //データ生成準備 (Set DataType)
        dataset();
        
        //各種ID(AgentID, AgentKey, MessageQueueID)の設定
        idset();
        
        System.out.println(toString());    
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" --- Data Setting Information --- \n");
        sb.append(DATA_TYPE.toString()+"\n");
        sb.append("\n");
        
        sb.append(" --- ID Setting Information --- \n");
        for(int i=0; i < NUMBER_OF_AGENTS; i++){
            sb.append(ID.toKey(i)+", ");
            sb.append(ID.toAGID(ID.toKey(i))+", ");
            sb.append(ID.toMQN(ID.toKey(i))+"\n");
        }
        
        return sb.toString();
    }
}
