package rda.test;

import java.util.ArrayList;
import rda.agent.CreateAgentClient;
import rda.agent.user.UpdateUser;
import rda.data.DataGenerator;
import rda.property.SetPropertry;

public class UpdateTest {
	private static final int NUM_OF_AGENTS = 1000;
	
	private static void update(int num){
		UpdateUser user = new UpdateUser();
		CreateAgentClient agentClient = new CreateAgentClient();

		DataGenerator ag = DataGenerator.getInstance();

		user.setParam(agentClient.getClient());

		for(int i=0; i < NUM_OF_AGENTS*num; i++){
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(ag.getData().data);
                    user.sendUpdateMessage(ag.getData().agentKey, list);
		}
	}

	public static void main(String[] args) {
		int num = 1;
		if(args.length != 0) num = Integer.valueOf(args[0]);

		update(Integer.valueOf(num));
	}
}
