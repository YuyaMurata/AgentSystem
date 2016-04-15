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
        
        user.setData(tx, user.getData(tx)+updateData);

        long updateCount = user.getConnectionCount(tx) + 1;
        user.setConnectionCount(tx, updateCount);

        // Update Log Records
        if(updateCount % 10000 == 0){
            String AccessID = String.valueOf(System.currentTimeMillis());
            Log log = user.createLog(tx, AccessID);

            // Update LastAccessTime
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            log.setLastAccessTime(tx, updateTime);
        }
		
        String message = avgLatency.toString();

        return message;
    }
}
