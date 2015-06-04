package rda.property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {
	public Property() {
		// TODO 自動生成されたコンストラクター・スタブ
		loadServerProperty();
		loadQueueProperty();
		loadAgentProperty();
	}

	private static Properties server = new Properties();
	private static Properties queue = new Properties();
	private static Properties agent = new Properties();

	//Load Property
	private void load(Properties prop, String filename){
		System.out.println("Load " + filename);

		try{
			prop.load(new FileInputStream(filename));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//Server Property
	private void loadServerProperty(){
		String filename = "server.property";
		load(server, filename);
	}

	//Queue Property
	private void loadQueueProperty(){
		String filename = "queue.property";
		load(queue, filename);
	}

	//Agent Property
	private void loadAgentProperty(){
		String filename = "agent.property";
		load(agent, filename);
	}

	//Get Value
	public String getValue(String prop, String key){
		switch (prop) {
		case "server":
			System.out.println("ServerProperty Read : "+key+"="+server.getProperty(key));
			return server.getProperty(key);
		case "queue":
			System.out.println("QueueProperty Read : "+key+"="+queue.getProperty(key));
			return queue.getProperty(key);
		case "agent":
			System.out.println("AgentProperty Read : "+key+"="+agent.getProperty(key));
			return agent.getProperty(key);
		default:
			return null;
		}
	}

}
