zip -r `hostname`_`date +%Y%m%d%H%M%S`.zip logs

#h1��dropbox�Ƀt�@�C�����ړ�����
sshpass -p 11m35584 scp `hostname`_*.zip root@h1:$CETA_HOME/App/dropbox_log

rm -f `hostname`_*.zip
