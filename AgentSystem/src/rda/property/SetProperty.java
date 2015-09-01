package rda.property;

import java.util.ArrayList;
import rda.data.MountData;


public interface SetProperty {
	public static final Property prop = new Property();
	//public static OutputData outputEvent = new OutputData("MQEvent_Ex_Mt_"+System.currentTimeMillis()+".txt");

	public static final String AGENT_TYPE = "useragent";
        
        public static final Integer AGENT_DEFAULT_VALUE = 1;
        
	//Server Property
	/**
	 * file:server.property
	 * h1.server=h1\:2809
	 * h0.server=localhost\:2809
	 * number.server=1
	 * number.thread=16
	 */
	public static final Integer NUMBER_OF_SERVER = Integer.valueOf(prop.getValue("server", "number.server"));
	public static final Integer SERVER_THREAD = Integer.valueOf(prop.getValue("server","number.thread"));
	public static ArrayList<String> HOST_ADDRESS = prop.setHost(NUMBER_OF_SERVER);

	//Queue Property
	/**
	 * file:queue.property
	 * size.window
	 * number.queue
	 * length.queue
	 * wait.queue
	 */
	public static final Integer WINDOW_SIZE = Integer.valueOf(prop.getValue("queue", "size.window"));
	public static final Integer NUMBER_OF_QUEUE = Integer.valueOf(prop.getValue("agent", "number.user.agent"));//Integer.valueOf(prop.getValue("queue", "number.queue"));
	public static final Integer QUEUE_LENGTH = Integer.valueOf(prop.getValue("queue", "length.queue"));
	public static final Long QUEUE_WAIT = Long.valueOf(prop.getValue("queue", "wait.queue"));

	//Agent Property
	/**
	 * file:agent.property
	 * number.user.agent
         * time.period=
	 * wait.agent
	 * time.run
	 * number.rank.agent
	 */
	public static final Integer TIME_RUN = Integer.valueOf(prop.getValue("agent", "time.run"));
	public static final Long TIME_PERIOD = Long.valueOf(prop.getValue("agent", "time.period")); // ms
	public static final Integer NUMBER_OF_USER_AGENTS = Integer.valueOf(prop.getValue("agent", "number.user.agent"));
	public static final Integer NUMBER_OF_RANK_AGENTS = Integer.valueOf(prop.getValue("agent", "number.rank.agent"));
	public static final Long AGENT_WAIT = Long.valueOf(prop.getValue("agent", "wait.agent"));
        public static final Integer DATA_VOLUME = Integer.valueOf(prop.getValue("agent", "data.volume"));;
        
        
        public static final MountData DATA_TYPE = new MountData();
}