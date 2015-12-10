#確認
sshpass -p 11m35584 ssh root@$1 'echo `hostname`'

read

#プログラムの実行
sshpass -p 11m35584 ssh root@$1 "cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;./git-remote.sh"
;./start.sh"

#サーバーの起動
sshpass -p 11m35584 ssh root@$1 \
"source /etc/profile;\
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
chmod 777 *.sh;\
./git-remote.sh;\
chmod 777 *.sh;\
./start.sh"

sleep 10

#プログラムの実行
sshpass -p 11m35584 ssh root@$1 \
"source /etc/profile;\
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
chmod 777 *.sh;\
./exec_system.sh 1"

#生成されたファイルを送る
sshpass -p 11m35584 ssh root@h2 \
"source /etc/profile;\
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
chmod 777 *.sh;\
./remote-script.sh"

sshpass -p 11m35584 ssh root@h2 \
"source /etc/profile;\
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
chmod 777 *.sh;\
./stop.sh"