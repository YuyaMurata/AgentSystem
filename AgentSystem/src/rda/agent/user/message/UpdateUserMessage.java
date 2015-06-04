package rda.agent.user.message;

import com.ibm.agent.exa.Message;

public class UpdateUserMessage extends Message{

	public int data;

	public void setParams(int data){
		this.data = data;
	}
}
