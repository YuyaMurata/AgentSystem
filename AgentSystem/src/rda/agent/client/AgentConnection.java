/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.client;

import com.ibm.agent.exa.client.AgentClient;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import rda.queue.manager.MessageQueueManager;

/**
 *
 * @author kaeru
 */
public class AgentConnection {
    private ObjectPool<AgentClient> _pool;
    private static final AgentConnection connector = new AgentConnection();
    private Integer NUMBER_OF_POOL =16;
    
    private static MessageQueueManager manager = MessageQueueManager.getInstance();
    
    private AgentConnection(){
        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
        conf.setMaxIdle(NUMBER_OF_POOL);
        conf.setMaxTotal(NUMBER_OF_POOL);
        
        this._pool = new GenericObjectPool<>(new AgentClientFactory("localhost:2809", "rda", "agent"), conf);
                
        System.out.println("***********************************************************");
        System.out.println("total:"+((GenericObjectPool) _pool).getMaxTotal()
                            +" , minIdle:"+((GenericObjectPool) _pool).getMinIdle()
                            + " , maxIdle:"+((GenericObjectPool) _pool).getMaxIdle());
        System.out.println("***********************************************************");
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

    public Integer getActiveObject(){
        return _pool.getNumActive();
    }
    
    public Integer getIdleObject(){
        return _pool.getNumIdle();
    }
}
