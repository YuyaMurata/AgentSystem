/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.queue.MessageQueue;
import rda.agent.rank.creator.CreateRankAgent;

/**
 *
 * @author 悠也
 */
public class AgentMessageQueueManager {
    private static AgentMessageQueueManager manager = new AgentMessageQueueManager();
    private Integer queueLength;
    private IDManager id;
    
    //Singleton
    private void AgentMessageQueueManager(){}
    public static AgentMessageQueueManager getInstance(){
        return manager;
    }
    
    public void initAgentMessageQueueManager(Map agentMQParam){
        
    }
    
    //Agentの複数生成 e.g.("R#", 10)
    public void createNumberOfAgents(String nameRule, Integer numOfAgents){
        this.id = new IDManager(nameRule);
        
        for(int i=0; i < numOfAgents; i++){
            String agID = id.genID();
            createAgent(agID);
        }
    }
    
    //Agentの単生成 e.g.("R#01")
    private void createAgent(String agID){
        CreateRankAgent rankAgent = new CreateRankAgent();
        rankAgent.create(agID, queueLength);
    }
    
    //MessageQueueの実行管理
    private static Boolean runnable =true;
    public Boolean isRunnable(){
        return runnable;
    }
    
    //全てのMessageQueueを終了する
    public void doShutdown(){
        runnable = false;
    }
    
    //Logger用にMQの監視オブジェクトを登録
    private static List<Object> observeList = new ArrayList<>();
    public void add(Object observe){
       observeList.add(observe);
    }
    
    //ManagerにMessageQueueを登録
    private static Map messageQueueMap = new HashMap();
    public void register(MessageQueue mq){
        messageQueueMap.put(mq.name, mq);
        mq.start();
    }
    
    public IDManager getIDManager(){
        return id;
    }
}
