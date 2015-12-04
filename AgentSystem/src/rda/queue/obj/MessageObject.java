package rda.queue.obj;

import com.ibm.agent.exa.AgentKey;

public class MessageObject {

	public AgentKey agentKey;
	public int data;

	public MessageObject(AgentKey agentKey, int data) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.agentKey = agentKey;
		this.data = data;
	}
	
        @Override
	public String toString(){
		return agentKey+","+data;
	}
}
