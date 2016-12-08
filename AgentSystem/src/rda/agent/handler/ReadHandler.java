package rda.agent.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rda.Aggregateagent;
import rda.agent.reader.AgentInfo;

public class ReadHandler extends MessageHandler {

    @Override
    public Object onMessage(Message arg0) throws Exception {
        Aggregateagent agent = (Aggregateagent) getEntity();

        TxID tx = getTx();

        AgentInfo info = new AgentInfo(
                /*AgentID*/agent.getAgentID(tx),
                /*data*/ agent.getData(tx),
                /*count */ agent.getConnectionCount(tx)
        );

        return info;
    }

}
