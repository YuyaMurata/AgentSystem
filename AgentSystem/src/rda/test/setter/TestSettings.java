/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.setter;

import rda.data.profile.ProfileGenerator;
import rda.data.DataGenerator;
import rda.data.type.MountData;
import rda.queue.id.IDToMQN;

/**
 *
 * @author kaeru
 */
public class TestSettings extends TestParameter {
    public static DataGenerator DATA_TYPE;
    private void dataset(){
        MountData type = new MountData(
                TIME_RUN, 
                TIME_PERIOD, 
                VOLUME, 
                NUMBER_OF_USER, 
                VALUE,
                DATA_MODE,
                Integer.MAX_VALUE
        );
        
        DATA_TYPE = new DataGenerator(type);
    }
    
    public static IDToMQN ID = IDToMQN.getInstance();
    private void idset(){
        for(int i=0; i < NUMBER_OF_AGENTS; i++){
            ID.createID();
        }
        
        ID.init();
    }
    
    private static int decompose = NUMBER_OF_AGENTS-1;
    public static void decomposeTest(String pid){
        String agID = ID.createID();
        
        ID.addDistAgent(pid, agID);
    }
    
    public void setting(){
        //データ生成準備 (Set DataType)
        dataset();
        
        //各種ID(AgentID, AgentKey, MessageQueueID)の設定
        idset();
        
        System.out.println(toString());    
    }
    
    public static String toTestSettingsString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" --- Data Setting Information --- \n");
        sb.append(DATA_TYPE.toString()+"\n");
        sb.append("\n");
        
        sb.append(" --- ID Setting Information --- \n");
        sb.append(ID.getAGIDListToString());
        
        return sb.toString();
    }
}
