package rda.agent.user.handler;

import java.sql.Timestamp;

import rda.Log;
import rda.Useragent;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rda.agent.user.message.UpdateUserMessage;
import rda.queue.obj.MessageObject;

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
        for(int i=0; i < updateMsg.data.size(); i++)
            updateData =  updateData + ((MessageObject)updateMsg.data.get(i)).data;
            
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
		
        String message = "";//"UpdateUser for data:"+updateData+" Log["+updateCount+"]";

        //System.out.println("Agent["+getAgentKey()+"]:"+message);

        return message;
    }
}