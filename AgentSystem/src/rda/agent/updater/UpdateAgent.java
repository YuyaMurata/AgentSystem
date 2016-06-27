package rda.agent.updater;

import java.util.Collection;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import java.util.List;
import rda.agent.client.AgentConnection;
import rda.agent.template.AgentType;
import rda.agent.message.UpdateMessage;
import rda.manager.LoggerManager;

public class UpdateAgent extends AgentType {
    /**
    *
    */
    private static final long serialVersionUID = -4245098133759745980L;
    private static final String AGENT_TYPE = "aggregateagent";
    private static final String MESSAGE_TYPE = "updateAgent";
    private static AgentConnection agcon;
    private String agID;
        
    public UpdateAgent(String agID){
        this.agID = agID;
        this.agcon = AgentConnection.getInstance();
        this.agentKey = new AgentKey(AGENT_TYPE, new Object[]{agID});
    }

    AgentKey agentKey;
    List data;
    public UpdateAgent(AgentKey agentKey, List data) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
        this.data = data;
    }

    @Override
    public Object complete(Collection<Object> results) {
        // TODO 自動生成されたメソッド・スタブ
        Object[] ret = results.toArray();
        return ret[0];
    }

    @Override
    public Object execute() {
        // TODO 自動生成されたメソッド・スタブ
        try {
            AgentManager agentManager = AgentManager.getAgentManager();
                
            MessageFactory factory = MessageFactory.getFactory();
            UpdateMessage msg = (UpdateMessage)factory.getMessage(MESSAGE_TYPE);
            msg.setParams(data);

            //Sync Message
            Object ret = agentManager.sendMessage(agentKey, msg);
            //agentManager.putMessage(agentKey, msg);

            return ret;
        } catch (IllegalAccessException | InstantiationException e) {
            // TODO 自動生成された catch ブロック
            return 0L;
        }
    }

    @Override
    public void sendMessage(Object data){
        if(data == null) return;
            
        try {
            AgentClient client = agcon.getConnection();
                
            UpdateAgent executor = new UpdateAgent(agentKey, (List)data);
            
            Object reply = client.execute(agentKey, executor);
            if(reply != null){
                try{
                    LoggerManager.getInstance().putMSGLatency(agID, (Long)reply);
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("rda.agent.rank.updater.UpdateRank.sendMessage()"+reply);
                }
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