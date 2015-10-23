/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.client;

import com.ibm.agent.exa.client.AgentClient;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 *
 * @author kaeru
 */
public class AgentConnection {
    private static final AgentClientFactory client = new AgentClientFactory("localhost:2809", "rda", "agent");
    private static ObjectPool<AgentClient> _pool;
    private static final AgentConnection connector = new AgentConnection(new GenericObjectPool<>(client));
    
    private AgentConnection(ObjectPool<AgentClient> pool){
        this._pool = pool;
    }
    
    public static AgentConnection getInstance(){
        return connector;
    }
    
    public AgentClient getConnection(){
        AgentClient ag = null;
        try {
            ag = _pool.borrowObject();
            return ag;
        } catch (Exception ex) {
            System.out.println("Not Connect AgentClient!");
        }
        return ag;
    }
    
    public void returnConnection(AgentClient ag){
        if(ag != null)
            try {
                _pool.returnObject(ag);
            } catch (Exception ex) {}
    }
    
    public void close(){
        _pool.close();
    }
}
