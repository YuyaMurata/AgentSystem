#確認
sshpass -p 11m35584 ssh root@$1 'echo `hostname`'
echo "確認後 Enter"
read

#サーバーの起動
sshpass -p 11m35584 ssh root@$1 \
"source /etc/profile;\
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
chmod 777 *.sh;\
./git-remote.sh;\
chmod 777 *.sh;\
./start.sh"

sleep 15

#プログラムの実行
sshpass -p 11m35584 ssh root@$1 \
"source /etc/profile;\
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
chmod 777 *.sh;\
./exec_system.sh 10"

#生成されたファイルを送る
sshpass -p 11m35584 ssh root@$1 \
"source /etc/profile;\
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
chmod 777 *.sh;\
./remote-script.sh"

#サーバーの終了
sshpass -p 11m35584 ssh root@$1 \
"source /etc/profile;\
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
chmod 777 *.sh;\
./stop.sh"

#ログをDropboxに送る
dropbox upload $CETA_HOME/App/dropbox_log/*.zip logs
mv $CETA_HOME/App/dropbox_log/*.zip old