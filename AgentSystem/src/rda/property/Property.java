package rda.property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {
    public Property() {
        // TODO 自動生成されたコンストラクター・スタブ
        loadPropertyALL();
        getValueALL();
    }

    private static final Properties server = new Properties();
    private static final Properties queue = new Properties();
    private static final Properties agent = new Properties();

    //Load Property
    private void load(Properties prop, String filename){
        System.out.println("* Load " + filename);

        try{
            prop.load(new FileInputStream(filename));
        }catch(IOException e){
                e.printStackTrace();
        }
    }

    //Server Property
    private void loadServerProperty(){
        String filename = "property/server.property";
        load(server, filename);
    }

    //Queue Property
    private void loadQueueProperty(){
        String filename = "property/queue.property";
        load(queue, filename);
    }

    //Agent Property
    private void loadAgentProperty(){
        String filename = "property/agent.property";
        load(agent, filename);
    }

    //Get Value
    public String getValue(String prop, String key){
        switch (prop) {
            case "server": return server.getProperty(key);
            case "queue" : return queue.getProperty(key);
            case "agent" : return agent.getProperty(key);
            default      : return null;
        }
    }
    
    private void loadPropertyALL(){
        System.out.println("*************** -Load Property File- ***************");
        
        loadServerProperty();
        loadQueueProperty();
        loadAgentProperty();
    }
    
    private void getValueALL(){
        System.out.println("*************** -Get Property Value- ***************");
        for(Object key: server.keySet())
            System.out.println("* ServerProperty Value : "+(String)key+"="+getValue("server", (String)key));
        System.out.println("* ------------------------------------------------ *");
        
        for(Object key: queue.keySet())
            System.out.println("* QueueProperty Value  : "+(String)key+"="+getValue("queue", (String)key));
        System.out.println("* ------------------------------------------------ *");
        
        for(Object key: queue.keySet())
            System.out.println("* AgentProperty Value  : "+(String)key+"="+getValue("agent", (String)key));
        System.out.println("****************************************************");
    }
    
    public static void main(String args[]){
        Property prop = new Property();
    }
}
