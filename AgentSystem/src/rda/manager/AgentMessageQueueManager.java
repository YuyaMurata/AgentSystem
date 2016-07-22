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
import rda.agent.creator.CreateAgent;
import rda.agent.logger.AggregateAgentLogPrinter;
import rda.window.WindowController;

/**
 *
 * @author 悠也
 */
public class AgentMessageQueueManager extends AgentManager{
    private static AgentMessageQueueManager manager = new AgentMessageQueueManager();
    private Boolean runnable = true;
    private Integer queueLength;
    private Long queuewait, agentwait;
    private Integer agentMode, reserveMode;
    
    private IDManager id;
    private WindowController windowCTRL;
    
    private List<QueueObserver> observes = new ArrayList<>();
    
    //Singleton
    private void AgentMessageQueueManager(){}
    
    public static AgentMessageQueueManager getInstance(){
        return manager;
    }
    
    public void initAgentMessageQueueManager(Map agentMQParam, Map idParam, Map windowParam){
        this.queueLength = (Integer)agentMQParam.get("QUEUE_LENGTH");
        this.queuewait = (Long)agentMQParam.get("QUEUE_WAIT");
        this.agentwait = (Long)agentMQParam.get("AGENT_WAIT");
        this.reserveMode = (Integer)agentMQParam.get("RESERVE_MODE");
        //this.runnable = true;
        
        //Init IDManager
        this.id = new IDManager(idParam);
        
        //Init LogPrinter
        AggregateAgentLogPrinter log = new AggregateAgentLogPrinter("aggregateagent");
        LoggerManager.getInstance().setLogPrinter(log);
        
        //Init AgentCloning
        this.agentMode = (Integer)agentMQParam.get("AGENT_MODE");
        
        //Init WindowController
        this.windowCTRL = new WindowController((Integer)windowParam.get("WINDOW_SIZE"),
                                          (Long)windowParam.get("ALIVE_TIME"),
                                          (Integer)windowParam.get("POOLSIZE"));
    }
    
    @Override
    public IDManager getIDManager(){
        return id;
    }
    
    //Agentの複数生成 e.g.("R#", 10)
    public void createNumberOfAgents(Integer numOfAgents){
        for(int i=0; i < numOfAgents; i++){
            String agentID = createAgent();
            id.initRegID(agentID);
        }
    }
    
    //Agentの単生成 e.g.("R#01")
    public String createAgent(){
        String agID;
        Object agent = null;
        
        if((agID = id.getReserveID()) == null){
            agID = id.genID();
            CreateAgent rankAgent = new CreateAgent();
            rankAgent.create(agID, queueLength, queuewait, agentwait);
        } else {
            System.out.println(">> Get Reserve Agent = "+agID);
        }
        
        return agID;
    }
    
    //Agentの複製 e.g.("R#01_Clone")
    @Override
    public String createCloneAgent(String originalID, Object originalState){
        String agID = createAgent();
        ((MessageQueue)getAgent(agID)).setOriginalQueue(originalState);
        
        return agID;
    }
    
/*    public void registerAgentID(String primalID, String referID){
        id.regID(primalID, referID);
    }
    
    public void reserveAgent(String reserveID){
        id.setReserveID(reserveID);
    }
    
    public void deleteAgentID(String primalID, String referID){
        id.deleteID(primalID, referID);
    }
*/    
    //MessageQueueの実行管理
    @Override
    public Boolean getState(){
        return runnable;
    }
    
    //全てのMessageQueueを終了する
    public void doShutdown(){
        runnable = false;
    }
    
    //Logger用にMQの監視オブジェクトを登録
    @Override
    public void add(QueueObserver observe){
       observes.add((QueueObserver) observe);
    }
    
    public List<QueueObserver> getObserver(){
        return observes;
    }
    
    //ManagerにMessageQueueを登録
    private Map messageQueueMap = new HashMap();
    public void register(MessageQueue mq){
        messageQueueMap.put(mq.getID(), mq);
        mq.start();
    }
    
    public Object getAgent(String agID){
        return messageQueueMap.get(agID);
    }
    
    @Override
    public Map getMQMap(){
        return messageQueueMap;
    }
    
    @Override
    public Integer getNumAgents(){
        return messageQueueMap.size() - id.getNumReserves();
    }
    
    @Override
    public Boolean getAutoMode(){
        return agentMode == 0;
    }
    
    public Integer getReserveMode(){
        return reserveMode;
    }

    @Override
    public WindowController getWindowController() {
        return this.windowCTRL;
    }
}