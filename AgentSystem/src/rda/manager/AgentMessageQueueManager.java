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
import rda.agent.queue.QueueObserver;
import rda.agent.rank.creator.CreateRankAgent;

/**
 *
 * @author 悠也
 */
public class AgentMessageQueueManager {
    private static AgentMessageQueueManager manager = new AgentMessageQueueManager();
    private Boolean runnable;
    private Integer queueLength;
    private Long queuewait, agentwait;
    private Integer agentMode, reserveMode;
    private IDManager id;
    
    //Singleton
    private void AgentMessageQueueManager(){}
    
    public static AgentMessageQueueManager getInstance(){
        return manager;
    }
    
    public void initAgentMessageQueueManager(Map agentMQParam){
        this.queueLength = (Integer)agentMQParam.get("QUEUE_LENGTH");
        this.queuewait = (Long)agentMQParam.get("QUEUE_WAIT");
        this.agentwait = (Long)agentMQParam.get("AGENT_WAIT");
        this.agentMode = (Integer)agentMQParam.get("AGENT_MODE");
        this.reserveMode = (Integer)agentMQParam.get("RESERVE_MODE");
        this.runnable = true;
    }
    
    //IDManager setter, getter
    public void setIDManager(IDManager id){
        this.id = id;
    }
    
    public IDManager getIDManager(){
        return id;
    }
    
    //Agentの複数生成 e.g.("R#", 10)
    public void createNumberOfAgents(Integer numOfAgents){
        for(int i=0; i < numOfAgents; i++){
            String agID = id.genID();
            createAgent(agID);
            id.initRegID(agID);
        }
    }
    
    //Agentの単生成 e.g.("R#01")
    public void createAgent(String agID){
        CreateRankAgent rankAgent = new CreateRankAgent();
        rankAgent.create(agID, queueLength, queuewait, agentwait);
    }
    
    //MessageQueueの実行管理
    public Boolean getState(){
        return runnable;
    }
    
    //全てのMessageQueueを終了する
    public void doShutdown(){
        runnable = false;
    }
    
    //Logger用にMQの監視オブジェクトを登録
    private static List<QueueObserver> observeList = new ArrayList<>();
    public void add(Object observe){
       observeList.add((QueueObserver) observe);
    }
    
    public List<QueueObserver> getObserver(){
        return observeList;
    }
    
    //ManagerにMessageQueueを登録
    private static Map messageQueueMap = new HashMap();
    public void register(MessageQueue mq){
        messageQueueMap.put(mq.name, mq);
        mq.start();
    }
    
    public Map getMQMap(){
        return messageQueueMap;
    }
    
    public Integer getAutoMode(){
        return agentMode;
    }
    
    public Integer getReserveMode(){
        return reserveMode;
    }
}