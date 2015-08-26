package rda.queue;

import java.util.ArrayList;

import rda.agent.CreateAgentClient;
import rda.agent.user.UpdateUser;

public class ReciveMQProcess extends Thread {
	private boolean running = true;
	
	private final String name;
	private final ReciveMessageQueue queue;
	public ReciveMQProcess(String name, ReciveMessageQueue queue) {
            // TODO 自動生成されたコンストラクター・スタブ
            System.out.println("********** Recive Message Queue "+name+" Start!! ********** ");
            
            this.name = name;
            this.queue = queue;
	}

	public void run() {
            // TODO 自動生成されたメソッド・スタブ
            CreateAgentClient ag = new CreateAgentClient();
                
            UpdateUser user = new UpdateUser();
            user.setParam(ag.getClient());
		
            while(running || queue.check()){
                ArrayList<MessageObject> messageList = (ArrayList<MessageObject>) queue.getMessage();
			
                //System.out.println(name+"_"+messageList.size()+":稼動中!");
			
                for(MessageObject msg : messageList){
                    //System.out.print("ReciveMessageQueue "+name+" execute Agent["+mes.toString()+"]");
                    user.sendUpdateMessage(msg.agentKey, msg.data);
                }
            }
		
            ag.close();
            System.out.println("********** Recive Message Queue "+name+" Stop!! ********** ");
	}

	public Thread stopRunning(){
		running = false;
                return this;
	}
}
