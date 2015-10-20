#Initialise
mkdir logs/history
rm -f vmstat.log
LOG_FD=user

#Setting CLASS PATH
export CLASSPATH=$CLASSPATH:../property:../resource:../library/Log/logback-access-1.1.3.jar:../library/Log/logback-classic-1.1.3.jar:../library/Log/logback-core-1.1.3.jar:../library/Log/slf4j-api-1.7.12.jar:../library/csv/opencsv-3.5.jar

#{1..20}
#30 40 50 60 70 80 90 100
for i in 10
do

    mkdir logs/${LOG_FD}${i}

    # Change Property (System Parameter)
    java -cp $CLASSPATH rda.property.RewriteProperty $i

    vmstat -n -S m -a 1 | awk '{print strftime("%Y-%m-%d %H:%M:%S.000"), $0} { system(":") }' > vmstat.log &

    java -cp $CLASSPATH -Xms4096m -Xmx4096m rda.main.Main
    java -cp $CLASSPATH rda.test.ReadTest

    java -cp $CLASSPATH rda.Dispose

    killall vmstat
    cat vmstat.log | awk '{print $1 " " $2 "," $15 "," $16}' > vmstat.csv

    cp logs/*.csv logs/${LOG_FD}${i}
    cp -r logs/history logs/${LOG_FD}${i}
    cp -r ../property logs/${LOG_FD}${i}

    rm -f logs/*.csv
    rm -f logs/history/*

    mv vmstat.* logs/${LOG_FD}${i}

    java -cp $CLASSPATH rda.result.ResultsDataForming ${LOG_FD}${i}

done