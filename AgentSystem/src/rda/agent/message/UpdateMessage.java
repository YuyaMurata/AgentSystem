package rda.agent.message;

import com.ibm.agent.exa.Message;
import rda.agent.template.MessageTemplate;

/**
 * エージェント更新を行うメッセージ．
 */
public class UpdateMessage extends Message{

    //Compound Data
    public MessageTemplate messageData;

    public void setParams(MessageTemplate messageData){
        this.messageData = messageData;
    }
}