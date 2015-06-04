package rda.agent.user;

import java.io.Serializable;
import java.util.Collection;

import rda.agent.user.message.UpdateUserMessage;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;

public class UpdateUser implements AgentExecutor, Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -4245098133759745980L;
	private static final String AGENT_TYPE = "useragent";
	private static final String MESSAGE_TYPE = "updateUserAgent";

	public UpdateUser() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	AgentKey agentKey;
	int data;
	public UpdateUser(AgentKey agentKey, int data) {
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
			UpdateUserMessage msg;
			msg = (UpdateUserMessage)factory.getMessage(MESSAGE_TYPE);
			msg.setParams(data);

			//Sync Message
			Object ret = agentManager.sendMessage(agentKey, msg);
			//agentManager.putMessage(agentKey, msg);

			return ret;
		} catch (IllegalAccessException | InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return e;
		}
	}

	AgentClient client;
	public void setParam(AgentClient client){
		this.client = client;
	}

	public void sendUpdateMessage(AgentKey agentKey, int data){
		try {
			UpdateUser executor = new UpdateUser(agentKey, data);
			client.execute(agentKey, executor);

			//System.out.println("A message from the agent[" + agentKey + "]: " + reply);
		} catch (AgentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			//reply = e;
		}
	}

}