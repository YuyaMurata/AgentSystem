package rda.property;

import java.util.ArrayList;

public interface SetProperty {
	public static final Property prop = new Property(true);
	 
	public static final String AGENT_TYPE = "useragent";
        
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
	public static final Integer NUMBER_OF_QUEUE = Integer.valueOf(prop.getValue("queue", "number.queue"));
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
        public static final Integer DATA_VOLUME = Integer.valueOf(prop.getValue("agent", "data.volume"));
        public static final Integer AGENT_DEFAULT_VALUE = Integer.valueOf(prop.getValue("agent", "agent.default.value"));
        
        //Log Property
        /**
         * log.dir=logs/
         * log.file=AgentSystem_info
         * log.history=history/
         * file.agent=MQLength
         * file.event=MQEvent
         * file.cpu=vmstat
         * file.results=AgentResults
         * file.all=ALLData
         */
        public static final String LOG_DIR = prop.getValue("log", "log.dir");
        public static final String LOG_FILE = prop.getValue("log", "log.file");
        public static final String LOG_MQL = prop.getValue("log", "file.agent");
        public static final String LOG_MQE = prop.getValue("log", "file.event");
        public static final String LOG_CPU = prop.getValue("log", "file.cpu");
        public static final String LOG_RESULTS = prop.getValue("log", "file.results");
        public static final String LOG_ALL = prop.getValue("log", "file.all");
        
}