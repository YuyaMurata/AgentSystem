package rda.agent.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import rda.agent.client.AgentConnection;

import rda.agent.user.message.InitUserMessage;
import rda.queue.IDToMQN;

public class CreateUserAgent implements AgentExecutor, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 856847026370330593L;
	public static final String AGENT_TYPE = "useragent";
	static final String MESSAGE_TYPE = "initUserAgent";
	
	public CreateUserAgent() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	AgentKey agentKey;
	HashMap<String, String> prof;
	public CreateUserAgent(AgentKey agentKey, HashMap<String, String> prof) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.agentKey = agentKey;
		this.prof = prof;
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
			if (agentManager.exists(agentKey)) {
				return "agent (" + agentKey + ") already exists";
			}
		
			agentManager.createAgent(agentKey);
		
		
			MessageFactory factory = MessageFactory.getFactory();
			InitUserMessage msg = (InitUserMessage)factory.getMessage(MESSAGE_TYPE);
		
			msg.setParams(prof.get("Name"), prof.get("Sex"), 
                                    prof.get("Age"), prof.get("Address"));
		
			Object ret = agentManager.sendMessage(agentKey, msg);
		
			return ret;
		} catch (AgentException | IllegalAccessException | InstantiationException e) {
		}
		
		return null;
	}
	
        public void create(String userID){
            AgentConnection ag = AgentConnection.getInstance();
            ProfileGenerator profileGen = ProfileGenerator.getInstance();
            
            AgentClient client = ag.getConnection();
            
            IDToMQN id = IDToMQN.getInstance();
            
            try {
                agentKey = new AgentKey(AGENT_TYPE,new Object[]{userID});
                id.setID(agentKey);
                
                prof = profileGen.getProf(userID);
                
                CreateUserAgent executor = new CreateUserAgent(agentKey, prof);
                Object reply = client.execute(agentKey, executor);
		
                System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");
            } catch (AgentException e) {
            } finally {
                //System.out.println("Active Num:"+ag.getActiveObject());
                //System.out.println("Idle Num:"+ag.getIdleObject());
                
                ag.returnConnection(client);
                
                //System.out.println("Active Num:"+ag.getActiveObject());
                //System.out.println("Idle Num:"+ag.getIdleObject());
            }
	}

}
