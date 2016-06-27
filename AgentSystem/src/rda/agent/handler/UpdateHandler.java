package rda.agent.handler;

import java.sql.Timestamp;

import rda.Log;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.List;
import rda.Aggregateagent;
import rda.agent.queue.MessageObject;
import rda.agent.message.UpdateMessage;

public class UpdateHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateMessage updateMsg = (UpdateMessage) msg;

        // マスターエンティティを取得
        Aggregateagent agent = (Aggregateagent)getEntity();
        
        // トランザクションIDを取得
        TxID tx = getTx();
        long updateData = 0;
        Long avgLatency = 0L;        
        for(MessageObject msgobj : (List<MessageObject>)updateMsg.messageData){
            updateData =  updateData + msgobj.data;
            avgLatency = avgLatency + msgobj.latency();
        }
        if(avgLatency > 0) avgLatency = avgLatency / updateMsg.messageData.size();
        
        agent.setData(tx, agent.getData(tx)+updateData);

        long updateCount = agent.getConnectionCount(tx) + 1;
        agent.setConnectionCount(tx, updateCount);

        // Update Log Records
        Log log = agent.getLog(tx, "update");
        if(log == null)
            log = agent.createLog(tx, "update");
        
        // Update LastAccessTime
        Long time = System.currentTimeMillis();
        Timestamp updateTime = new Timestamp(time);
        log.setLastAccessTime(tx, updateTime);
        log.setCurrentTime(tx, time);
        
        Long message = avgLatency;
        
        return message;
    }
}