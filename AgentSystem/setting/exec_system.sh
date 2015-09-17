export CLASSPATH=$CLASSPATH:../property:../resource:../library/Log/logback-access-1.1.3.jar:../library/Log/logback-classic-1.1.3.jar:../library/Log/logback-core-1.1.3.jar:../library/Log/slf4j-api-1.7.12.jar

for i in 10
do

java -cp $CLASSPATH rda.property.RewriteProperty $i

vmstat -n -S m -a 1 | awk '{print strftime("%Y-%m-%d %H:%M:%S.000"), $0} { system(":") }' > vmstat.log &
java -cp $CLASSPATH rda.main.Main

killall vmstat
cat vmstat.log | awk '{print $1$2 "," $15 "," $16}' > vmstat.csv

java -cp $CLASSPATH rda.test.ReadTest

mkdir logs/user$i

cp logs/*.csv logs/user$i
cp -r logs/history logs/user$i

rm -f logs/*.csv
rm -f logs/history/*

mv vmstat.* logs/user$i

java -cp $CLASSPATH rda.Dispose

done