package rda.agent.template;

import com.ibm.agent.exa.client.AgentExecutor;
import java.io.Externalizable;

public abstract class AgentType implements AgentExecutor, Externalizable{
    public abstract void sendMessage(Object data); 
    public abstract String getID();
}
