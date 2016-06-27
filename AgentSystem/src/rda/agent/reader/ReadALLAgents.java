/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.reader;

import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

import rda.agent.client.AgentConnection;

/**
 *
 * @author kaeru
 */
public class ReadALLAgents implements AgentExecutor, Serializable{
    private static final String MESSAGE_TYPE = "readAgent";

    public ReadALLAgents() {
    }
    
    @Override
    public Object execute() {
        try {
            AgentManager agentManager = AgentManager.getAgentManager();
            // DISPOSEメッセージの生成
            MessageFactory factory = MessageFactory.getFactory();
            Message msg = factory.getMessage(MESSAGE_TYPE);

            HashMap< AgentKey,Object> ret = agentManager.sendMessage(msg);
            return ret;
        } catch(IllegalAccessException | InstantiationException e) {
            return e;
        }
    }

    @Override
    public Object complete(Collection<Object> results) {
        return results;
    }
    
    public Collection<AgentInfo> read() {
        // エージェントクライアント
        AgentConnection ag = AgentConnection.getInstance();
        
        try {
            //クライアントの接続
            AgentClient client = ag.getConnection();
            
            // エージェントエグゼキュータを生成
            ReadALLAgents executor = new ReadALLAgents();

            // エージェントエグゼキュータを全エージェント実行環境に転送し，
            // その集約結果を取得．集約結果は，completeメソッドの戻り値．
            Object ret = client.execute(executor);
            
            TreeMap map = new TreeMap();

            // 全エージェント実行環境からの結果を取得
            Collection<Object> retFromAllServers = (Collection<Object>)ret;
            for(Object obj : retFromAllServers) {
                // 各エージェント実行環境でのReadメッセージの戻り値を取得．
                // 処理結果はHashMapとなる．
                HashMap<AgentKey, Object> retFromAgents = (HashMap<AgentKey, Object>)obj;
                for(AgentKey agentKey : retFromAgents.keySet()) {
                    AgentInfo info = (AgentInfo)retFromAgents.get(agentKey);
                    map.put(agentKey.toString(), info);
                }
            }
            //クライアントの切断
            ag.returnConnection(client);
            
            //TestPrint
            for(Object key : map.keySet()){
                System.out.println(key + "[");
                System.out.println("    " + ((AgentInfo)map.get(key)).toString());
                System.out.println("]");
                
            }
            
            return map.values();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    } 
}
