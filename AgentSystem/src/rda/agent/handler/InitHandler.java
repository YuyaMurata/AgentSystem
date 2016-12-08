package rda.agent.handler;

import java.sql.Timestamp;

import rda.Log;
import rda.agent.message.InitMessage;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rda.Aggregateagent;
import rda.Aggregatecondition;

public class InitHandler extends MessageHandler {
    @Override
    public Object onMessage(Message msg) throws Exception {
        try {
            InitMessage initMsg = (InitMessage)msg;

            Aggregateagent agent = (Aggregateagent)getEntity();

            TxID tx = getTx();
            Aggregatecondition cond = agent.createCondition(tx);

            cond.setConditions(tx, initMsg.condition);
                        
            Long time = System.currentTimeMillis();
            Timestamp registerTime = new Timestamp(time);
            cond.setLastAccessTime(tx, registerTime);

            agent.setData(tx, 0);
            
            agent.setConnectionCount(tx, 0);
            
            agent.setMessageLatency(tx, 0);
            agent.setMessageQueueLength(tx, 0);

            Log log = agent.createLog(tx, "init");

            log.setLastAccessTime(tx, registerTime);
            log.setCurrentTime(tx, time);

            //System.out.println("InitHandler of Agent:" + getAgentKey() + " was initialized");

            return "hello from agent:" + getAgentKey();
        } catch(Exception e) {
            throw e;
        }
    }
}
