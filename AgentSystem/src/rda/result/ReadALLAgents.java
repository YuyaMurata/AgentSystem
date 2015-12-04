/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import rda.agent.client.AgentConnection;

/**
 *
 * @author kaeru
 */
public class ReadALLAgents implements AgentExecutor, Serializable{
    private static final String MESSAGE_TYPE = "readUserAgent";
    
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
    
    public static void main(String[] args) {
        // エージェントクライアントの生成
        AgentConnection ag = AgentConnection.getInstance();
        AgentClient client = ag.getConnection();
        
        try {
            // エージェントエグゼキュータを生成
            ReadALLAgents executor = new ReadALLAgents();

            // エージェントエグゼキュータを全エージェント実行環境に転送し，
            // その集約結果を取得．集約結果は，completeメソッドの戻り値．
            Object ret = client.execute(executor);

            // 全エージェント実行環境からの結果を取得
            Collection<Object> retFromAllServers = (Collection<Object>)ret;
            for(Object o : retFromAllServers) {
                // 各エージェント実行環境でのDISPOSEメッセージの戻り値を取得．
                // 処理結果はHashMapとなる．
                HashMap<AgentKey, Object> retFromAgents = (HashMap<AgentKey, Object>)o;
                Set<AgentKey> keySet = retFromAgents.keySet();
                for(AgentKey agentKey : keySet) {
                    String message = (String)retFromAgents.get(agentKey);
                    System.out.println(agentKey + ":" + message);
                }
            }	
        } catch(Exception e) {
        } finally {
            ag.returnConnection(client);
        }
        
        ag.close();
        
    }
    
}
