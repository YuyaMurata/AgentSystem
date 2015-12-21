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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import rda.agent.client.AgentConnection;
import rda.agent.user.reader.UserInfo;

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
    
    public Collection<UserInfo> read() {
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
            
            ArrayList<UserInfo> list = new ArrayList<>();
            TreeMap resultsMap = new TreeMap();

            // 全エージェント実行環境からの結果を取得
            Collection<Object> retFromAllServers = (Collection<Object>)ret;
            for(Object o : retFromAllServers) {
                // 各エージェント実行環境でのReadメッセージの戻り値を取得．
                // 処理結果はHashMapとなる．
                HashMap<AgentKey, Object> retFromAgents = (HashMap<AgentKey, Object>)o;
               
                Set<AgentKey> keySet = retFromAgents.keySet();
                for(AgentKey agentKey : keySet) {
                    UserInfo info = (UserInfo)retFromAgents.get(agentKey);
                    
                    //Map
                    resultsMap.put(agentKey.toString(), info);
                }
            }
            
            //Test Print
            System.out.println("TreeMap:"+resultsMap.size());
            for(Object key : resultsMap.keySet()){
                System.out.println(key + "[");
                System.out.println("    " + ((UserInfo)resultsMap.get(key)).toString());
                System.out.println("]");
            }
            
            //クライアントの切断
            ag.returnConnection(client);
            
            return resultsMap.values();
        } catch(Exception e) {
            return null;
        } finally {
            ag.close();
        }
    }
    
}
