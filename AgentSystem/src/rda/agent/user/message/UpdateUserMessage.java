package rda.agent.user.message;

import com.ibm.agent.exa.Message;
import java.util.ArrayList;

public class UpdateUserMessage extends Message{

	public ArrayList data;

	public void setParams(ArrayList data){
		this.data = data;
	}
}
