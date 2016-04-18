package rda.agent.rank.updater;

import java.util.Collection;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import java.util.List;
import rda.agent.client.AgentConnection;
import rda.agent.template.AgentType;
import rda.agent.user.message.UpdateUserMessage;
import rda.manager.LoggerManager;

public class UpdateRank extends AgentType {
    /**
    *
    */
    private static final long serialVersionUID = -4245098133759745980L;
    private static final String AGENT_TYPE = "useragent";
    private static final String MESSAGE_TYPE = "updateUserAgent";
    private static AgentConnection agcon;
    private String agID;
        
    public UpdateRank(String agID){
        this.agID = agID;
        this.agcon = AgentConnection.getInstance();
        this.agentKey = new AgentKey(AGENT_TYPE, new Object[]{agID});
    }

    AgentKey agentKey;
    List data;
    public UpdateRank(AgentKey agentKey, List data) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
        this.data = data;
    }

    @Override
    public Object complete(Collection<Object> results) {
        // TODO 自動生成されたメソッド・スタブ
        Object[] ret = results.toArray();
        return ret;
    }

    @Override
    public Object execute() {
        // TODO 自動生成されたメソッド・スタブ
        try {
            AgentManager agentManager = AgentManager.getAgentManager();
                
            MessageFactory factory = MessageFactory.getFactory();
            UpdateUserMessage msg = (UpdateUserMessage)factory.getMessage(MESSAGE_TYPE);
            msg.setParams(data);

            //Sync Message
            Object ret = agentManager.sendMessage(agentKey, msg);
            //agentManager.putMessage(agentKey, msg);

            return ret;
        } catch (IllegalAccessException | InstantiationException e) {
            // TODO 自動生成された catch ブロック
            return e;
        }
    }

    @Override
    public void sendMessage(Object data){
        if(data == null) return;
            
        try {
            AgentClient client = agcon.getConnection();
                
            UpdateRank executor = new UpdateRank(agentKey, (List)data);
                
            Object reply = client.execute(agentKey, executor);
            int i=0;
            if(reply != null){
                for(Object rep : (Object[])reply)
                System.out.println(">>> REPLY"+(i++)+" = "+rep);
            }
            agcon.returnConnection(client);
        } catch (AgentException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public String getID() {
        return this.agID;
    }
}