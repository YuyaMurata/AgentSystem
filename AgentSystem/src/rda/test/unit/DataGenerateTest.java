/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import java.util.HashMap;
import java.util.Map;
import rda.manager.TestCaseManager;
import rda.queue.obj.MessageObject;
import rda.test.param.TestParameter;

/**
 *
 * @author kaeru
 */
public class DataGenerateTest extends TestParameter{
    private static Map validate = new HashMap();
    
    public static void main(String[] args) {
        TestCaseManager tcmanager = TestCaseManager.getInstance();
        tcmanager.initTestCase(dataParam, profParam);
        
        //Data Generate-Test
        MessageObject msg;
        Long msgcnt = 0L;
        while((msg = tcmanager.datagen.generate(TIME_RUN)) != null){
            System.out.println("Message:"+msgcnt+":["+msg.toString()+"]");
            msgcnt++;
            
            //Validation (ID & Message Data)
            if(validate.get(msg.id) == null) validate.put(msg.id, 0);
            else if((Integer)validate.get(msg.id) != -1){
                Integer msgValue = (Integer)validate.get(msg.id) + msg.data;
                validate.put(msg.id, msgValue);
            }
        }
        
        //Output Results
        System.out.println(">>Validation Results:{");
        for(Object id : validate.keySet()){
            System.out.println("    id="+id+"-"+validate.get(id));
        }
        System.out.println("}");
    }
}