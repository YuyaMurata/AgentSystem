/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

import com.ibm.agent.exa.Message;

/**
 *
 * @author kaeru
 */
public class PutMessage extends Message {
    
    public Object msgPack;
	
    // パラメータをセット
    public void setParams(Object msgPack) {
        this.msgPack = msgPack;
    }
}
