package rda.agent.user.handler;

import java.sql.Timestamp;

import rda.Log;
import rda.Useragent;
import rda.agent.user.message.UpdateUserMessage;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;

public class UpdateUserHandler extends MessageHandler{

	@Override
	public Object onMessage(Message msg) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		UpdateUserMessage updateMsg = (UpdateUserMessage) msg;

		// マスターエンティティを取得
		Useragent user = (Useragent)getEntity();

		// トランザクションIDを取得
		TxID tx = getTx();

		int updateData = user.getData(tx) + updateMsg.data;
		user.setData(tx, updateData);

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
