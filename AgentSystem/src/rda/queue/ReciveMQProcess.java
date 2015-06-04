package rda.queue;

import java.util.ArrayList;

import rda.agent.CreateAgentClient;
import rda.agent.user.UpdateUser;

import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.soliddb.gridagent.CETADataGridException;

public class ReciveMQProcess extends Thread {
	private boolean running = true;
	
	private String name;
	private ReciveMessageQueue queue;
	public ReciveMQProcess(String name, ReciveMessageQueue queue) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.name = name;
		this.queue = queue;
	}

	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		AgentClient ag = null;
		try {
			ag = new AgentClient("localhost:2809","cetaadmin","cetaadmin",null,"rda","agent");
		} catch (CETADataGridException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}//new CreateAgentClient();
		UpdateUser user = new UpdateUser();
		user.setParam(ag);
		
		while(running || queue.check()){
			ArrayList<MessageObject> messageList = (ArrayList<MessageObject>) queue.getMessage();
			
			//System.out.println(name+"_"+messageList.size()+":稼動中!");
			
			for(MessageObject mes : messageList){
				//System.out.print("ReciveMessageQueue "+name+" execute Agent["+mes.toString()+"]");
				user.sendUpdateMessage(mes.agentKey, mes.data);
			}
		}
		
		ag.close();
		System.out.println("Thread "+name+" Stop!!");
	}

	public void stopRunning(){
		running = false;
	}
}
