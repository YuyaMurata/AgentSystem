package rda.agent.user.message;

import com.ibm.agent.exa.Message;
import java.util.List;

public class UpdateUserMessage extends Message{

    //Compound Data
    public List messageData;

    public void setParams(List messageData){
        this.messageData = messageData;
    }
}
