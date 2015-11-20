/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import rda.data.SetDataType;
import static rda.data.SetDataType.DATA_TYPE;
import rda.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class DataTest extends TestParameter implements SetDataType{
    public static void main(String[] args) {
        MessageObject msg;
        for(long t =0; t < TIME_RUN; t++)
            while((msg = DATA_TYPE.generate(t)) != null)
                System.out.println("MSG["+msg.agentKey+","+msg.data+"]");
    }
}
