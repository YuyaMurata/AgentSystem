#Setting CLASS PATH
export CLASSPATH=$CLASSPATH:../property:../resource:../library/Log/logback-access-1.1.3.jar:../library/Log/logback-classic-1.1.3.jar:../library/Log/logback-core-1.1.3.jar:../library/Log/slf4j-api-1.7.12.jar:../library/csv/opencsv-3.5.jar

for i in 10
do

mkdir logs/user$i

java -cp $CLASSPATH rda.property.RewriteProperty $i

vmstat -n -S m -a 1 | awk '{print strftime("%Y-%m-%d %H:%M:%S.000"), $0} { system(":") }' > vmstat.log &

java -cp $CLASSPATH rda.main.Main
java -cp $CLASSPATH rda.test.ReadTest

java -cp $CLASSPATH rda.Dispose

killall vmstat
cat vmstat.log | awk '{print $1 " " $2 "," $15 "," $16}' > vmstat.csv

cp logs/*.csv logs/user$i
cp -r logs/history logs/user$i
cp -r ../property logs/user$i

rm -f logs/*.csv
rm -f logs/history/*

mv vmstat.* logs/user$i

java -cp $CLASSPATH rda.result.ResultsDataForming $i

done