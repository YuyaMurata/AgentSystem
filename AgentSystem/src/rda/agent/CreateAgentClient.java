package rda.agent;

import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.soliddb.gridagent.CETADataGridException;

public class CreateAgentClient {
	private AgentClient client;
	public CreateAgentClient() {
		// TODO 自動生成されたコンストラクター・スタブ
		try {
			this.setClient(new AgentClient("localhost:2809","cetaadmin","cetaadmin",null,"rda","agent"));
		} catch (CETADataGridException e) {
			// TODO 自動生成された catch ブロック
			System.out.println("Get AgentClient Exception");
		}
	}
        
	public synchronized AgentClient getClient() {
		return this.client;
	}
        
	private void setClient(AgentClient client) {
		this.client = client;
	}

	public void close(){
		this.client.close();
	}
}
