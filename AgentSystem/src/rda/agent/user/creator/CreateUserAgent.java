package rda.agent.user.creator;

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
import rda.agent.profile.AgentProfileGenerator;
import rda.agent.user.message.InitUserMessage;

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
	
        public void create(String agID){
            try {
                AgentConnection ag = AgentConnection.getInstance();
                AgentProfileGenerator profileGen = AgentProfileGenerator.getInstance();
                
                AgentClient client = ag.getConnection();
                
                agentKey = new AgentKey(AGENT_TYPE,new Object[]{agID});
                
                prof = profileGen.genAgentProfile(agID);
                
                CreateUserAgent executor = new CreateUserAgent(agentKey, prof);
                Object reply = client.execute(agentKey, executor);
		
                System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");
                
                ag.returnConnection(client);
                
            } catch (AgentException e) {
            }
	}

}