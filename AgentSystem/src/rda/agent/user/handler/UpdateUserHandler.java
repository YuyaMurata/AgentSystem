package rda.agent.user.handler;

import java.sql.Timestamp;

import rda.Log;
import rda.Useragent;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rda.agent.queue.MessageObject;
import rda.agent.user.message.UpdateUserMessage;

public class UpdateUserHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateUserMessage updateMsg = (UpdateUserMessage) msg;

        // マスターエンティティを取得
        Useragent user = (Useragent)getEntity();

        // トランザクションIDを取得
        TxID tx = getTx();
        long updateData = 0;
        Long avgLatency = 0L;
        for(int i=0; i < updateMsg.data.size(); i++){
            MessageObject msgobj = (MessageObject)updateMsg.data.get(i);
            updateData =  updateData + msgobj.data;
            avgLatency = avgLatency + msgobj.latency();
        }
        avgLatency = avgLatency / updateMsg.data.size();
        
        user.setData(tx, user.getData(tx)+updateData);

        long updateCount = user.getConnectionCount(tx) + 1;
        user.setConnectionCount(tx, updateCount);

        // Update Log Records
        if(user.getLog(tx, "update") == null) user.createLog(tx, "update");
        Log log = user.getLog(tx, "update");
        
        // Update LastAccessTime
        Long time = System.currentTimeMillis();
        Timestamp updateTime = new Timestamp(time);
        log.setLastAccessTime(tx, updateTime);
        log.setCurrentTime(tx, time.toString());
        
        Long message = avgLatency;
        
        System.out.println(">> **********MSG="+message);
        
        return message;
    }
}
