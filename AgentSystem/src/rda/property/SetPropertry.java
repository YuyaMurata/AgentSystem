package rda.property;

import java.util.ArrayList;

import rda.data.OutputData;

public abstract class SetPropertry {
	public static Property prop = new Property();
	public static OutputData out = new OutputData("Ex_Mt_"+System.currentTimeMillis()+".txt");

	public static final String AGENT_TYPE = "useragent";

	//Server Property
	/**
	 * file:server.property
	 * #Thu Aug 14 15:12:48 JST 2014
	 * h1.server=h1\:2809
	 * h0.server=localhost\:2809
	 * number.server=1
	 * number.thread=16
	 */
	public static final Integer NUMBER_OF_SERVER = Integer.valueOf(prop.getValue("server", "number.server"));
	public static final Integer SERVER_THREAD = Integer.valueOf(prop.getValue("server","number.thread"));
	public static ArrayList<String> HOST_ADDRESS = new ArrayList<String>();
	public void setHost(){
		for(int i=0; i < NUMBER_OF_SERVER; i++)
			HOST_ADDRESS.add(prop.getValue("server", "h"+i+".server"));
	}

	//Queue Property
	/**
	 * file:queue.property
	 * #Thu Aug 14 15:12:48 JST 2014
	 * size.window=100
	 * number.queue=1
	 * length.queue=1000
	 * wait.queue=10
	 */
	public static final Integer WINDOW_SIZE = Integer.valueOf(prop.getValue("queue", "size.window"));
	public static final Integer NUMBER_OF_QUEUE = Integer.valueOf(prop.getValue("agent", "number.user.agent"));//Integer.valueOf(prop.getValue("queue", "number.queue"));
	public static final Integer QUEUE_LENGTH = Integer.valueOf(prop.getValue("queue", "length.queue"));
	public static final Long QUEUE_WAIT = Long.valueOf(prop.getValue("queue", "wait.queue"));

	//Agent Property
	/**
	 * file:agent.property
	 * #Thu Aug 14 15:12:48 JST 2014
	 * number.user.agent=1
	 * wait.agent=10
	 * time.run=300
	 * number.rank.agent=1
	 */
	public static final Integer TIME_RUN = Integer.valueOf(prop.getValue("agent", "time.run"));
	public static final Long TIME_PERIOD = (long) 1000; // 1sec
	public static final Integer NUMBER_OF_USER_AGENTS = Integer.valueOf(prop.getValue("agent", "number.user.agent"));
	public static final Integer NUMBER_OF_RANK_AGENTS = Integer.valueOf(prop.getValue("agent", "number.rank.agent"));
	public static final Long AGENT_WAIT = Long.valueOf(prop.getValue("agent", "wait.agent"));
}
