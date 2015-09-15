export CLASSPATH=$CLASSPATH:../property:../resource:../library/Log/logback-access-1.1.3.jar:../library/Log/logback-classic-1.1.3.jar:../library/Log/logback-core-1.1.3.jar:../library/Log/slf4j-api-1.7.12.jar

vmstat -n -S m -a 1 | awk '{print strftime("%H:%M:%S"), $0} { system(":") }' > vmstat.log &
java -cp $CLASSPATH rda.main.Main

killall vmstat

mkdir logs/user
cp logs/*.log logs/user
cp -r logs/history logs/user
rm -f logs/*.log
rm -f logs/history/*
cp vmstat.log logs/user
echo "" > logs/user/clsql_result

./clex.sh 
